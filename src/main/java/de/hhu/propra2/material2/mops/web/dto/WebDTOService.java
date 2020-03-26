package de.hhu.propra2.material2.mops.web.dto;


import de.hhu.propra2.material2.mops.database.DTOs.DateiDTO;
import de.hhu.propra2.material2.mops.database.DTOs.GruppeDTO;
import de.hhu.propra2.material2.mops.database.DTOs.UserDTO;
import de.hhu.propra2.material2.mops.database.Repository;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.ArrayList;
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

    /**
     * Method the controller will call to update our database
     * with the new Groups and users from the RestAPI
     * @param update
     * @throws SQLException
     */
    public void updateDatabase(final UpdatedGroupRequestMapper update) throws SQLException {
        startUpdate(update);
        updateLeavingUsers(update);
    }


    private Map<String, HashMap<String, Boolean>> startUpdate(final UpdatedGroupRequestMapper update)
            throws SQLException {
        /**
         * delete deprecated groups
         **/
        Map<String, GroupWebDTO> gruppen = getPrunedGroups(update.getGroupList());
        /**
         * Map userIds from update to the corresponding UserWebDTO
         */
        Map<String, UserWebDTO> usersWeb = new HashMap<>();
        /**
         * Map userIds from update to groupWebDTO
         */
        Map<String, HashMap<String, Boolean>> belegungWeb = new HashMap<>();

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

        List<String> groupsWithUpdate = getIdFromGroupsWithUpdates(update);
        Map<String, HashMap<String, Boolean>> updatedAndSynchronizedUserGroupRelation =
                addNewGroups(usersWeb, belegungWeb, groupsWithUpdate);
        saveUserInDatabase(gruppen, usersWeb, updatedAndSynchronizedUserGroupRelation);
        return updatedAndSynchronizedUserGroupRelation;
    }

    private void saveUserInDatabase(final Map<String, GroupWebDTO> gruppen,
                                    final Map<String, UserWebDTO> usersWeb,
                                    final Map<String, HashMap<String, Boolean>> updatedAndSynchronizedUserGroupRelation)
            throws SQLException {
        for (String userId : usersWeb.keySet()) {
            HashMap<GruppeDTO, Boolean> belegung = loadBelegung(gruppen,
                    updatedAndSynchronizedUserGroupRelation,
                    userId);
            repository.saveUser(loadUser(usersWeb.get(userId), belegung));
        }
    }

    /**
     * Returns a map which maps a user which comes up in the update to
     * all groups which he has joined and also to
     * the groups he was a member of in our database but not if he left them
     * in the update
     *
     * @param updatedUsers
     * @param updatedBelegungen
     * @param updatedGroups
     * @return
     * @throws SQLException
     */
    private Map<String, HashMap<String, Boolean>> addNewGroups(final Map<String, UserWebDTO> updatedUsers,
                                                               final Map<String,
                                                                       HashMap<String, Boolean>> updatedBelegungen,
                                                               final List<String> updatedGroups) throws SQLException {

        Map<String, HashMap<String, Boolean>> oldAndNewGroups =
                putUsersInGroups(updatedUsers, updatedGroups);
        return getUnionOfOldAndNewGroupsWithRights(updatedBelegungen, oldAndNewGroups);
    }

    private void updateLeavingUsers(final UpdatedGroupRequestMapper update) throws SQLException {
        List<String> groupsWithUpdates = getIdFromGroupsWithUpdates(update);
        List<GroupWebDTO> gruppenWeb = update.getGroupList();
        HashMap<String, List<String>> usersInAGroup = getUsersInAGroupDatabase(groupsWithUpdates);

        for (GroupWebDTO gruppeWeb : gruppenWeb) {
            List<String> updatedUsersInAGroup = getIdsFromUpdatedUsers(gruppeWeb);
            deleteUserGroupRelationshipOfLeavingUser(usersInAGroup, gruppeWeb, updatedUsersInAGroup);
        }
    }

    /**
     * Check if in updatedBelegungen a user is in a group, which is not in oldAndNewGroups,
     * if so, add it
     */
    private Map<String, HashMap<String, Boolean>> getUnionOfOldAndNewGroupsWithRights(
            final Map<String, HashMap<String, Boolean>> updatedBelegungen,
            final Map<String, HashMap<String, Boolean>> oldAndNewGroups) {
        Set<String> userIdsForBelegungen = updatedBelegungen.keySet();

        for (String keyUserId : userIdsForBelegungen) {
            HashMap<String, Boolean> groupsAndRights = updatedBelegungen.get(keyUserId);
            Set<String> groupIdsForRights = groupsAndRights.keySet();
            for (String keyGroupId : groupIdsForRights) {
                Set<String> groupsOfAUser = oldAndNewGroups.get(keyUserId).keySet();
                if (!groupsOfAUser.contains(keyGroupId)) {
                    boolean berechtigung = groupsAndRights.get(keyGroupId);
                    oldAndNewGroups.get(keyUserId).put(keyGroupId, berechtigung);
                }
            }
        }

        return oldAndNewGroups;
    }

    /**
     * returns a map where each user is in all groups in which he has to be
     * after the update.
     * That means he is in the groups he joined and is not in those he left.
     *
     * @param updatedUsers
     * @param updatedGroups
     * @return
     * @throws SQLException
     */
    private Map<String, HashMap<String, Boolean>> putUsersInGroups(final Map<String, UserWebDTO> updatedUsers,
                                                                   final List<String> updatedGroups)
            throws SQLException {
        Map<String, HashMap<String, Boolean>> oldAndNewGroups = new HashMap<>();

        for (String userId : updatedUsers.keySet()) {
            UserDTO userDTO = repository.findUserByKeycloakname(userId);
            oldAndNewGroups.put(userId, new HashMap<>());
            for (GruppeDTO keyGruppeDTO : userDTO.getBelegungUndRechte().keySet()) {
                if (!updatedGroups.contains(keyGruppeDTO.getId())) {
                    oldAndNewGroups
                            .get(userId)
                            .put(keyGruppeDTO.getId(), userDTO.getBelegungUndRechte().get(keyGruppeDTO));
                }
            }
        }

        return oldAndNewGroups;
    }

    private Map<String, GroupWebDTO> getPrunedGroups(final List<GroupWebDTO> groupList) throws SQLException {
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

    private List<String> getIdFromGroupsWithUpdates(final UpdatedGroupRequestMapper update) {
        return update.getGroupList()
                .stream()
                .map(GroupWebDTO::getId)
                .collect(Collectors.toList());
    }

    private List<String> getIdsFromUpdatedUsers(final GroupWebDTO gruppeWeb) {
        return gruppeWeb.getMembers().stream().map(UserWebDTO::getId).collect(Collectors.toList());
    }

    /**
     * Maps groupIds to the List of the Ids of their members
     */
    private HashMap<String, List<String>> getUsersInAGroupDatabase(final List<String> groupsWithUpdates)
            throws SQLException {
        HashMap<String, List<String>> usersInAGroup = new HashMap<>();
        for (String groupId : groupsWithUpdates) {
            usersInAGroup.put(groupId, repository.getUsersByGruppenId(groupId));
        }
        return usersInAGroup;
    }

    private boolean getBerechtigung(final String rolle) {
        if ("ADMIN".equals(rolle)) {
            return true;
        }
        return false;
    }

    private void deleteUserGroupRelationshipOfLeavingUser(final HashMap<String, List<String>> usersInAGroup,
                                                          final GroupWebDTO gruppeWeb,
                                                          final List<String> updatedUsersInAGroup) throws SQLException {
        List<String> usersPerGroup = usersInAGroup.get(gruppeWeb.getId());
        for (String userId : usersPerGroup) {
            if (!updatedUsersInAGroup.contains(userId)) {
                UserDTO userDTO = repository.findUserByKeycloakname(userId);
                GruppeDTO gruppeDTO = userDTO.getGruppeById(userId);
                repository.deleteUserGroupRelationByUserDTOAndGruppeDTO(userDTO, gruppeDTO);
            }
        }
    }

    private UserDTO loadUser(final UserWebDTO userWeb, final HashMap<GruppeDTO, Boolean> gruppen) {
        return new UserDTO(userWeb.getGivenname(), userWeb.getFamilyname(), userWeb.getId(), gruppen);
    }


    private GruppeDTO loadGruppe(final GroupWebDTO groupWeb) {
        String id = groupWeb.getId();
        List<DateiDTO> dateien = new ArrayList<>();
        return new GruppeDTO(id, groupWeb.getTitle(), groupWeb.getDescription(), dateien);
    }

    private HashMap<GruppeDTO, Boolean> loadBelegung(final Map<String, GroupWebDTO> gruppen,
                                                     final Map<String, HashMap<String, Boolean>> userBelegungMap,
                                                     final String userId) {
        HashMap<GruppeDTO, Boolean> belegung = new HashMap<>();
        for (String keyGroupId : userBelegungMap.get(userId).keySet()) {
            belegung.put(loadGruppe(gruppen.get(keyGroupId)), userBelegungMap.get(userId).get(keyGroupId));
        }
        return belegung;
    }
}
