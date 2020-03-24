package de.hhu.propra2.material2.mops.web.dto;


import de.hhu.propra2.material2.mops.Database.DTOs.DateiDTO;
import de.hhu.propra2.material2.mops.Database.DTOs.GruppeDTO;
import de.hhu.propra2.material2.mops.Database.DTOs.UserDTO;
import de.hhu.propra2.material2.mops.Database.Repository;
import org.bouncycastle.jcajce.provider.digest.SHA3;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class WebDTOService {

    private final Repository repository;

    public WebDTOService(final Repository repositoryArg) {
        this.repository = repositoryArg;
    }

    /**
     * @param update
     */
    public void updateDatabase(final UpdatedGroupRequestMapper update) throws SQLException {
        deleteGroups(update.getGroupList());
        Map<String, UserWebDTO> users = new HashMap<>();
        Map<String, HashMap<GruppeDTO, Boolean>> belegung = new HashMap<>();

        for (GroupWebDTO groupWeb : update.getGroupList()) {
            GruppeDTO gruppe = loadGruppe(groupWeb);
            for (UserWebDTO userWeb : groupWeb.getMembers()) {
                if (!users.containsKey(userWeb.getId())) {
                    users.put(userWeb.getId(), userWeb);
                }
                if (!belegung.containsKey(userWeb.getId())) {
                    belegung.put(userWeb.getId(), new HashMap<>());
                }
                belegung.get(userWeb.getId()).put(loadGruppe(groupWeb),
                        getBerechtigung(groupWeb.getRoles().get(userWeb.getId())));
            }
        }
        List<UserDTO> updatedUserList = new ArrayList<>();
        for (String key : users.keySet()) {
            updatedUserList.add(loadUser(users.get(key), belegung.get(key)));
        }
        addNewGroups(updatedUserList);
    }

    private void deleteOldGroupUserRelations(GruppeDTO gruppe) {

    }

    private boolean getBerechtigung(final String rolle) {
        if ("ADMIN".equals(rolle)) {
            return true;
        }
        return false;
    }

    private void addNewGroups(final List<UserDTO> updated) throws SQLException {
        /**
         *Here the users from the Database will be saved
         **/
        HashMap<String, UserDTO> databaseUser = new HashMap<>();
        /**
         *Iterate over all the users with updates
         **/
        for (UserDTO user : updated) {
            /**
             *Put the database version of all the users with updates in the map
             **/
            UserDTO userInDB = repository.findUserByKeycloakname(user.getKeycloakname());
            databaseUser.put(userInDB.getKeycloakname(), userInDB);
        }
        for (UserDTO user : updated) {
            for (GruppeDTO gruppe : user.getBelegungUndRechte().keySet()) {
                /**
                 * Check if the updated user has a group which he does not have in the database, if so, add it
                 */
                if (!generateGroupIdList(databaseUser
                        .get(user.getKeycloakname()).getBelegungUndRechte())
                        .contains(gruppe.getId())) {
                    databaseUser.get(user.getKeycloakname()).getBelegungUndRechte()
                            .put(gruppe, user.getBelegungUndRechte().get(gruppe));
                }
            }
        }
    }

    private List<String> generateGroupIdList(final HashMap<GruppeDTO, Boolean> gruppen) {
        return gruppen.keySet().stream().map(GruppeDTO::getId).collect(Collectors.toList());
    }

    private void deleteGroups(final List<GroupWebDTO> groupList) throws SQLException {
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
            }
        }
    }

    private UserDTO loadUser(final UserWebDTO userWeb, final HashMap<GruppeDTO, Boolean> gruppen) {
        return new UserDTO(userWeb.getGivenname(), userWeb.getFamilyname(), userWeb.getId(), gruppen);
    }


    private GruppeDTO loadGruppe(final GroupWebDTO groupWeb) {
        long id = generateIdFromUUId(groupWeb.getId());
        List<DateiDTO> dateien = new ArrayList<>();
        return new GruppeDTO(id, groupWeb.getTitle(), groupWeb.getDescription(), dateien);
    }

    private long generateIdFromUUId(final String uuid) {
        //return uuid.getMostSignificantBits() & Long.MAX_VALUE;
        SHA3.DigestSHA3 digestSHA3 = new SHA3.Digest512();
        byte[] digest = digestSHA3.digest(uuid.getBytes());
        return (long) Arrays.hashCode(digest);
    }
}
