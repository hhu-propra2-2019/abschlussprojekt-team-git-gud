package de.hhu.propra2.material2.mops.web.dto;


import de.hhu.propra2.material2.mops.database.DTOs.DateiDTO;
import de.hhu.propra2.material2.mops.database.DTOs.GruppeDTO;
import de.hhu.propra2.material2.mops.database.DTOs.UserDTO;
import de.hhu.propra2.material2.mops.database.Repository;
import org.bouncycastle.jcajce.provider.digest.SHA3;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class WebDTOService {

    private final Repository repository;

    public WebDTOService(final Repository repositoryArg) {
        this.repository = repositoryArg;
    }

    private boolean getBerechtigung(final String rolle) {
        if ("ADMIN".equals(rolle)) {
            return true;
        }
        return false;
    }

    /**
     * Returns a map which maps a user which comes up in the update to
     * all groups which he has joined and also to
     * the groups he was a member of in our database but not if he left them
     * in the update
     *
     * @param updatedUsers
     * @param updatedBelegungen
     * @param groupsWithUpdate
     * @return
     * @throws SQLException
     */
    private Map<String, HashMap<String, Boolean>> addNewGroups(final Map<String, UserWebDTO> updatedUsers,
                                                               final Map<String,
                                                                       HashMap<String, Boolean>> updatedBelegungen,
                                                               final Set<String> groupsWithUpdate)
            throws SQLException {
        Map<String, HashMap<String, Boolean>> oldAndNewGroups = new HashMap<>();
        for (String userId : updatedUsers.keySet()) {
            UserDTO userDTO = repository.findUserByKeycloakname(userId);
            oldAndNewGroups.put(userId, new HashMap<>());

            for (GruppeDTO keyGruppeDTO : userDTO.getBelegungUndRechte().keySet()) {
                if (!groupsWithUpdate.contains(keyGruppeDTO.getId())) {
                    oldAndNewGroups
                            .get(userId)
                            .put(keyGruppeDTO.getId(), userDTO.getBelegungUndRechte().get(keyGruppeDTO));
                }
            }
        }
        /**
         * Check if in updatedBelegungen a user is in a group, which is not in oldAndNewGroups,
         * if so, add it
         */
        for (String keyUserId : updatedBelegungen.keySet()) {
            for (String keyGroupId : updatedBelegungen.get(keyUserId).keySet()) {
                Set<String> groupsOfAUser = oldAndNewGroups.get(keyUserId).keySet();
                if (!groupsOfAUser.contains(keyGroupId)) {
                    boolean berechtigung = updatedBelegungen.get(keyUserId).get(keyGroupId);
                    oldAndNewGroups.get(keyUserId).put(keyGroupId, berechtigung);
                }
            }
        }
        return oldAndNewGroups;
    }

    private Map<String, GroupWebDTO> deleteGroups(final List<GroupWebDTO> groupList) throws SQLException {
        /**
         * Map groupIds to corresponding GroupWebDTOs
         */
        Map<String, GroupWebDTO> gruppen = new HashMap<>();
        for (GroupWebDTO gruppe : groupList) {
            if (gruppe.getMembers() == null
                    && gruppe.getDescription() == null
                    && gruppe.getParent() == null
                    && gruppe.getRoles() == null
                    && gruppe.getTitle() == null
                    && gruppe.getType() == null
                    && gruppe.getVisibility() == null
                    && gruppe.getUserMaximum() == 0) {
                repository.deleteGroupByGroupDTO(loadGruppe(gruppe));
            } else {
                gruppen.put(gruppe.getId(), gruppe);
            }
        }
        return gruppen;
    }

    private UserDTO loadUser(final UserWebDTO userWeb, final HashMap<GruppeDTO, Boolean> gruppen) {
        return new UserDTO(userWeb.getGivenname(), userWeb.getFamilyname(), userWeb.getId(), gruppen);
    }


    private GruppeDTO loadGruppe(final GroupWebDTO groupWeb) {
        String id = groupWeb.getId();
        List<DateiDTO> dateien = new ArrayList<>();
        return new GruppeDTO(id, groupWeb.getTitle(), groupWeb.getDescription(), dateien);
    }

    /**
     * Can be called from Controller to save the changes from update into the Database
     *
     * @param update
     * @throws SQLException
     */
    public void startUpdate(final UpdatedGroupRequestMapper update) throws SQLException {
        /**
         * delete deprecated groups
         **/
        Map<String, GroupWebDTO> gruppen;
        gruppen = deleteGroups(update.getGroupList());
        /**
         * Map userIds from update to the corresponding UserWebDTO
         */
        Map<String, UserWebDTO> usersWeb = new HashMap<>();
        /**
         * Map userIds from update to groupWebDTO
         */
        Map<String, HashMap<String, Boolean>> belegungWeb = new HashMap<>();
        /**
         * Map userIds to UserDTOs These Users will be saved back into the Database
         */
        Map<UserWebDTO, HashMap<GruppeDTO, Boolean>> saveInDB = new HashMap<>();

        for (String key : gruppen.keySet()) {
            GroupWebDTO groupWeb = gruppen.get(key);
            for (UserWebDTO userWeb : groupWeb.getMembers()) {
                if (!usersWeb.containsKey(userWeb.getId())) {
                    usersWeb.put(userWeb.getId(), userWeb);
                }
                if (!belegungWeb.containsKey(userWeb.getId())) {
                    belegungWeb.put(userWeb.getId(), new HashMap<>());
                }
                belegungWeb.get(userWeb.getId()).put(groupWeb.getId(),
                        getBerechtigung(groupWeb.getRoles().get(userWeb.getId())));
            }
        }
        Set<String> groupsWithUpdate = update.getGroupList().stream().map(GroupWebDTO::getId).collect(Collectors.toSet());
        belegungWeb = addNewGroups(usersWeb, belegungWeb, groupsWithUpdate);
        for (String userId : usersWeb.keySet()) {
            HashMap<GruppeDTO, Boolean> belegungDTO = new HashMap<>();
            for (String keyGroupId : belegungWeb.get(userId).keySet()) {
                belegungDTO.put(loadGruppe(gruppen.get(keyGroupId)), belegungWeb.get(userId).get(keyGroupId));
            }
            repository.saveUser(loadUser(usersWeb.get(userId), belegungDTO));
        }
    }

    private void updateLeavingUsers(final UpdatedGroupRequestMapper update) throws SQLException {
        List<String> groupsWithUpdates = update.getGroupList()
                .stream()
                .map(GroupWebDTO::getId)
                .collect(Collectors.toList());
        List<GroupWebDTO> gruppenWeb = update.getGroupList();
        /**
         * Maps groupIds to the List of the Ids of their members
         */
        HashMap<String, List<String>> usersInAGroup = new HashMap<>();
        for (String groupId : groupsWithUpdates) {
            usersInAGroup.put(groupId, repository.getUsersByGruppenId(groupId));
        }
        for (GroupWebDTO gruppeWeb : update.getGroupList()) {
            List<String> memberNames = gruppeWeb.getMembers().stream().map(UserWebDTO::getId).collect(Collectors.toList());
            for (String userId : usersInAGroup.get(gruppeWeb.getId())) {
                if (!memberNames.contains(userId)) {
                    UserDTO userDTO = repository.findUserByKeycloakname(userId);
                    GruppeDTO gruppeDTO = userDTO.getGruppeById(userId);
                    repository.deleteUserGroupRelationByUserDTOAndGruppeDTO(userDTO, gruppeDTO);
                }
            }
        }
    }
}
