package de.hhu.propra2.material2.mops.domain.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.hhu.propra2.material2.mops.database.DTOs.GruppeDTO;
import de.hhu.propra2.material2.mops.database.DTOs.UserDTO;
import de.hhu.propra2.material2.mops.database.Repository;
import de.hhu.propra2.material2.mops.domain.services.dto.UpdatedGroupRequestMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assumptions.assumeTrue;
import static org.junit.jupiter.api.Assumptions.assumeFalse;
import static org.hamcrest.Matchers.is;

@ExtendWith(MockitoExtension.class)
public class WebDTOServiceTest {

    @Mock
    private RestTemplate serviceAccountRestTemplate;
    @Mock
    private Repository repoMock;

    private GruppeDTO gruppeDTO1;
    private GruppeDTO gruppeDTO2;
    private UserDTO userDTO0;
    private UserDTO userDTO1;
    private UserDTO userDTO2;

    private GroupWebDTO gruppe1Web;
    private GroupWebDTO gruppe2Web;
    private UserWebDTO user0Web;
    private UserWebDTO user1Web;
    private UserWebDTO user2Web;

    private String jsonExample;
    private BufferedReader bufferedReader;
    private UpdatedGroupRequestMapper group;

    private WebDTOService service;

    @BeforeEach
    private void setup() throws IOException {
        jsonExample = "";
        File file = new File("src/main/resources/example.json");
        bufferedReader = new BufferedReader(new FileReader(file));
        bufferedReader.lines().forEach(string -> jsonExample = jsonExample.concat(string));
        ObjectMapper mapper = new ObjectMapper();
        group = mapper.readValue(file, UpdatedGroupRequestMapper.class);

        service = new WebDTOService(repoMock);

        gruppeDTO1 = new GruppeDTO("1", null, null, null);
        gruppeDTO2 = new GruppeDTO("2", null, null, null);

        userDTO0 = new UserDTO(null, null, "user0", new HashMap<>());
        userDTO1 = new UserDTO(null, null, "user1", new HashMap<>());
        userDTO2 = new UserDTO(null, null, "user2", new HashMap<>());

        user0Web = new UserWebDTO();
        user0Web.setId("user0");
        user1Web = new UserWebDTO();
        user1Web.setId("user1");
        user2Web = new UserWebDTO();
        user2Web.setId("user2");

        gruppe1Web = new GroupWebDTO();
        gruppe1Web.setId("1");
        gruppe2Web = new GroupWebDTO();
        gruppe2Web.setId("2");
    }


    /**
     * Just to see if we modeled the WebDTO classes in the right way
     * concerning the JSON-File
     */
    @Test
    public void testConversionOfUser() {
        Mockito.when(serviceAccountRestTemplate.getForEntity("http://localhost:8080/gruppe2//api/updateGroups/0",
                UpdatedGroupRequestMapper.class)).thenReturn(new ResponseEntity(group, HttpStatus.OK));

        UpdatedGroupRequestMapper updatedGroupRequestMapper = serviceAccountRestTemplate
                .getForEntity("http://localhost:8080/gruppe2//api/updateGroups/0", UpdatedGroupRequestMapper.class)
                .getBody();

    }

    @Test
    public void updateContainsNothingNothingIsChanged() throws SQLException {
        UpdatedGroupRequestMapper update = new UpdatedGroupRequestMapper();
        update.setGroupList(new ArrayList<>());

        Map<String, HashMap<String, Boolean>> result = service.updateGroupUserRelations(update);
        assertThat(result.keySet().isEmpty(), is(true));
    }

    @Test
    public void userJoinsAGroup() throws SQLException {
        when(repoMock.findUserByKeycloakname("user1")).thenReturn(userDTO1);
        doNothing().when(repoMock).saveUser(any(UserDTO.class));

        UpdatedGroupRequestMapper update = new UpdatedGroupRequestMapper();
        List<UserWebDTO> users = new ArrayList<>();
        users.add(user1Web);
        gruppe1Web.setMembers(users);
        List<GroupWebDTO> gruppen = new ArrayList<>();
        gruppen.add(gruppe1Web);
        update.setGroupList(gruppen);
        Map<String, String> roles = new HashMap<>();
        roles.put(user1Web.getId(), "MEMBER");
        gruppe1Web.setRoles(roles);

        Map<String, HashMap<String, Boolean>> result = service.updateGroupUserRelations(update);
        Map<String, Boolean> groupsFromUser1 = result.get(user1Web.getId());

        assertThat(result.keySet().contains(user1Web.getId()), is(true));
        assertThat(groupsFromUser1.keySet().contains(gruppe1Web.getId()), is(true));
        assertThat(groupsFromUser1.get(gruppe1Web.getId()), is(false));
    }

    @Test
    public void twoUsersJoinAGroupOneMemberOneAdmin() throws SQLException {
        when(repoMock.findUserByKeycloakname("user1")).thenReturn(userDTO1);
        when(repoMock.findUserByKeycloakname("user2")).thenReturn(userDTO2);
        doNothing().when(repoMock).saveUser(any(UserDTO.class));

        UpdatedGroupRequestMapper update = new UpdatedGroupRequestMapper();
        List<UserWebDTO> users = new ArrayList<>();
        users.add(user1Web);
        users.add(user2Web);
        gruppe1Web.setMembers(users);
        List<GroupWebDTO> gruppen = new ArrayList<>();
        gruppen.add(gruppe1Web);
        update.setGroupList(gruppen);
        Map<String, String> roles = new HashMap<>();
        roles.put(user1Web.getId(), "MEMBER");
        roles.put(user2Web.getId(), "ADMIN");
        gruppe1Web.setRoles(roles);

        Map<String, HashMap<String, Boolean>> result = service.updateGroupUserRelations(update);
        Map<String, Boolean> groupsFromUser1 = result.get(user1Web.getId());
        Map<String, Boolean> groupsFromUser2 = result.get(user2Web.getId());

        assertThat(result.keySet().contains(user1Web.getId()), is(true));
        assertThat(result.keySet().contains(user2Web.getId()), is(true));
        assertThat(groupsFromUser1.keySet().contains(gruppe1Web.getId()), is(true));
        assertThat(groupsFromUser2.keySet().contains(gruppe1Web.getId()), is(true));
        assertThat(groupsFromUser1.get(gruppe1Web.getId()), is(false));
        assertThat(groupsFromUser2.get(gruppe1Web.getId()), is(true));
    }

    @Test
    public void oneUserJoinsTwoGroupsDifferentRoles() throws SQLException {
        when(repoMock.findUserByKeycloakname("user1")).thenReturn(userDTO1);
        doNothing().when(repoMock).saveUser(any(UserDTO.class));

        UpdatedGroupRequestMapper update = new UpdatedGroupRequestMapper();
        List<UserWebDTO> users1 = new ArrayList<>();
        List<UserWebDTO> users2 = new ArrayList<>();
        users1.add(user1Web);
        users2.add(user1Web);

        gruppe1Web.setMembers(users1);
        gruppe2Web.setMembers(users2);

        List<GroupWebDTO> gruppen = new ArrayList<>();
        gruppen.add(gruppe1Web);
        gruppen.add(gruppe2Web);
        update.setGroupList(gruppen);
        Map<String, String> roles1 = new HashMap<>();
        Map<String, String> roles2 = new HashMap<>();

        roles1.put(user1Web.getId(), "MEMBER");
        roles2.put(user1Web.getId(), "ADMIN");
        gruppe1Web.setRoles(roles1);
        gruppe2Web.setRoles(roles2);

        Map<String, HashMap<String, Boolean>> result = service.updateGroupUserRelations(update);
        Map<String, Boolean> groupsFromUser1 = result.get(user1Web.getId());

        assertThat(result.keySet().contains(user1Web.getId()), is(true));
        assertThat(groupsFromUser1.keySet().contains(gruppe1Web.getId()), is(true));
        assertThat(groupsFromUser1.keySet().contains(gruppe2Web.getId()), is(true));
        assertThat(groupsFromUser1.get(gruppe1Web.getId()), is(false));
        assertThat(groupsFromUser1.get(gruppe2Web.getId()), is(true));
    }

    @Test
    public void userLeavesAGroupAndJoinsAnother() throws SQLException {
        when(repoMock.findUserByKeycloakname("user1")).thenReturn(userDTO1);
        doNothing().when(repoMock).saveUser(any(UserDTO.class));
        HashMap<GruppeDTO, Boolean> belegung = new HashMap<>();
        belegung.put(gruppeDTO1, false);
        userDTO1 = new UserDTO(null, null, "user1", belegung);

        List<UserWebDTO> users = new ArrayList<>();
        users.add(user1Web);
        gruppe2Web.setMembers(users);
        gruppe1Web.setMembers(new ArrayList<>());
        HashMap<String, String> belegungUpdate = new HashMap<>();
        belegungUpdate.put(user1Web.getId(), "MEMBER");
        gruppe2Web.setRoles(belegungUpdate);
        UpdatedGroupRequestMapper update = new UpdatedGroupRequestMapper();
        List<GroupWebDTO> gruppen = new ArrayList<>();
        gruppen.add(gruppe2Web);
        gruppen.add(gruppe1Web);
        update.setGroupList(gruppen);

        Map<String, HashMap<String, Boolean>> result = service.updateGroupUserRelations(update);
        Map<String, Boolean> belegungUser1 = result.get(user1Web.getId());
        assertThat(result.keySet().contains(user1Web.getId()), is(true));
        assertThat(belegungUser1.keySet().contains(gruppe2Web.getId()), is(true));
        assertThat(belegungUser1.keySet().contains(gruppe1Web.getId()), is(false));
    }

    @Test
    public void userStaysInAGroupAndIsSomewhereInTheUpdate() throws SQLException {
        doNothing().when(repoMock).saveUser(any(UserDTO.class));

        List<UserWebDTO> users = new ArrayList<>();
        users.add(user1Web);

        HashMap<String, String> roles = new HashMap<>();
        roles.put(user1Web.getId(), "MEMBER");

        gruppe1Web.setMembers(users);
        gruppe1Web.setRoles(roles);

        List<GroupWebDTO> gruppen = new ArrayList<>();
        gruppen.add(gruppe1Web);

        UpdatedGroupRequestMapper update = new UpdatedGroupRequestMapper();
        update.setGroupList(gruppen);

        HashMap<GruppeDTO, Boolean> belegung = new HashMap<>();
        belegung.put(gruppeDTO2, false);

        userDTO1 = new UserDTO(null, null, "user1", belegung);
        when(repoMock.findUserByKeycloakname("user1")).thenReturn(userDTO1);

        Map<String, HashMap<String, Boolean>> result = service.updateGroupUserRelations(update);
        Map<String, Boolean> belegungUser1 = result.get(user1Web.getId());

        assertThat(belegungUser1.keySet().contains(gruppeDTO1.getId()), is(true));
        assertThat(belegungUser1.keySet().contains(gruppeDTO2.getId()), is(true));
    }

    /**
     * Braucht eien Return Wert bei WebDTOService.updateLeavingUsers
    @SuppressWarnings("checkstyle:InnerAssignment")
    @Test
    public void userLeavesAGroup() throws SQLException {
        HashMap<GruppeDTO, Boolean> belegung = new HashMap<>();
        belegung.put(gruppeDTO1, false);
        userDTO1 = new UserDTO(null, null, "user1", belegung);
        when(repoMock.findUserByKeycloakname("user1")).thenReturn(userDTO1);

        ArrayList<String> userIds = new ArrayList<>();
        userIds.add(userDTO1.getKeycloakname());

        gruppe1Web.setMembers(new ArrayList<>());
        gruppe1Web.setRoles(new HashMap<>());

        List<GroupWebDTO> gruppen = new ArrayList<>();
        gruppen.add(gruppe1Web);

        UpdatedGroupRequestMapper update = new UpdatedGroupRequestMapper();
        update.setGroupList(gruppen);
        when(repoMock.getUsersByGruppenId(gruppeDTO1.getId())).thenReturn(userIds);
        HashMap<String, String> result = service.updateLeavingUsers(update);
        assertThat(result.keySet().contains(userDTO1.getKeycloakname()), is(true));
        assertThat(result.get(userDTO1.getKeycloakname()), is(gruppeDTO1.getId()));

    }
    **/
}
