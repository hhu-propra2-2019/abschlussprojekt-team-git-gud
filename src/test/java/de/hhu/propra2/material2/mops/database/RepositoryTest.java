package de.hhu.propra2.material2.mops.database;

import de.hhu.propra2.material2.mops.Material2Application;
import de.hhu.propra2.material2.mops.database.DTOs.DateiDTO;
import de.hhu.propra2.material2.mops.database.DTOs.GruppeDTO;
import de.hhu.propra2.material2.mops.database.DTOs.TagDTO;
import de.hhu.propra2.material2.mops.database.DTOs.UserDTO;
import org.junit.Ignore;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.sql.SQLException;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;


@SpringBootTest(classes = Material2Application.class)
public final class RepositoryTest {

    private Repository repository;
    private GruppeDTO gruppe;
    private UserDTO user;
    private TagDTO tag;
    private ArrayList<TagDTO> tags = new ArrayList<TagDTO>();
    private DateiDTO datei;
    private ArrayList<DateiDTO> dateien = new ArrayList<DateiDTO>();
    private HashMap<GruppeDTO, Boolean> berechtigung = new HashMap<GruppeDTO, Boolean>();

    @SuppressWarnings("checkstyle:magicnumber")
    @Autowired
    public RepositoryTest(final Repository repositoryArg) {
        repository = repositoryArg;

        tag = new TagDTO("gae");
        tags.add(tag);

        gruppe = new GruppeDTO(99999999, "gruppe", "this is a description", dateien);
        berechtigung.put(gruppe, true);

        user = new UserDTO(999999, "Why are you gae?", "You are gae",
                "gae", berechtigung);

        datei = new DateiDTO("gaedata", user, tags, LocalDate.of(2020, 3, 01),
                LocalDate.now(), 200, "gae", gruppe, "gae");
        gruppe.getDateien().add(datei);
    }

    @BeforeEach
    public void preparation() throws SQLException {
        repository.deleteAll();
        repository.saveUser(user);
        datei.setId(repository.saveDatei(datei));
    }

    private ArrayList<UserDTO> generateXUsersWithYGroupsWithZFilesWithATags(final int userCount,
                                                                            final int groupCountPerUser,
                                                                            final int fileCountPerGroup,
                                                                            final int tagCountPerFile) {
        UserDTO[] users = new UserDTO[userCount];
        for (int i = 0; i < userCount; i++) {
            users[i] = generateRandomUser();
            for (int j = 0; j < groupCountPerUser; j++) {
                GruppeDTO gruppeDTO = generateRandomGruppe();
                for (int k = 0; k < fileCountPerGroup; k++) {
                    gruppeDTO.getDateien().add(generateRandomDatei(generateRandomTags(tagCountPerFile), gruppeDTO));
                }
                users[i].getBelegungUndRechte().put(gruppeDTO, true);
            }
        }
        return new ArrayList<UserDTO>(Arrays.asList(users));
    }

    private ArrayList<TagDTO> generateRandomTags(final int tagCount) {
        ArrayList<TagDTO> tagDTOs = new ArrayList<TagDTO>();
        for (int i = 0; i < tagCount; i++) {
            tagDTOs.add(new TagDTO(UUID.randomUUID().toString()));
        }
        return tagDTOs;
    }

    @SuppressWarnings("checkstyle:MagicNumber")
    private DateiDTO generateRandomDatei(final ArrayList<TagDTO> tagsArg, final GruppeDTO gruppeDTO) {
        return new DateiDTO(UUID.randomUUID().toString(),
                new UserDTO(-1, "User", "Deleted", "-", null),
                tagsArg, LocalDate.now(), LocalDate.now(),
                200, UUID.randomUUID().toString(),
                gruppeDTO, UUID.randomUUID().toString());
    }

    private GruppeDTO generateRandomGruppe() {
        return new GruppeDTO(UUID.randomUUID().getMostSignificantBits() & Long.MAX_VALUE,
                " ", " ", new ArrayList<DateiDTO>());
    }

    private UserDTO generateRandomUser() {
        return new UserDTO(UUID.randomUUID().getMostSignificantBits() & Long.MAX_VALUE,
                " ", " ", UUID.randomUUID().toString(), new HashMap<GruppeDTO, Boolean>());
    }

    @Test
    @SuppressWarnings("checkstyle:magicnumber")
    public void loadUserTest() throws SQLException {
        UserDTO userDTO = repository.findUserByKeycloaknameEager("gae");

        assertTrue(userDTO.getVorname().equals("Why are you gae?"));
        assertTrue(userDTO.getNachname().equals("You are gae"));
        assertTrue(userDTO.getId() == 999999);
        for (GruppeDTO gruppeDTO : userDTO.getBelegungUndRechte().keySet()) {
            assertTrue(userDTO.getBelegungUndRechte().get(gruppeDTO));
        }
    }

    @Test
    @SuppressWarnings("checkstyle:magicnumber")
    public void loadGruppeTest() throws SQLException {
        GruppeDTO gruppeDto = repository.findGruppeByGruppeIdEager(99999999);

        assertTrue(gruppeDto.getName().equals("gruppe"));
        assertTrue(gruppeDto.getDescription().equals("this is a description"));
    }

    @Test
    @SuppressWarnings("checkstyle:magicnumber")
    public void updateDateiTest() throws SQLException {
        ArrayList<TagDTO> newTags = new ArrayList<TagDTO>();
        TagDTO tag1 = new TagDTO("gae1");
        TagDTO tag2 = new TagDTO("gae2");
        DateiDTO newDatei;

        newTags.add(tag1);
        newTags.add(tag2);

        LocalDate newVeroeffentlichungsDatum = LocalDate.of(2020, 3, 10);
        newDatei = new DateiDTO(datei.getId(), "gaedata",
                user, newTags, LocalDate.now(), newVeroeffentlichungsDatum, 400, "fish", gruppe, "gaee");


        repository.saveDatei(newDatei);

        UserDTO userDTO = repository.findUserByKeycloaknameEager("gae");
        List<TagDTO> tagDTOS = new ArrayList<>();
        for (GruppeDTO gruppeDTO : userDTO.getBelegungUndRechte().keySet()) {
            tagDTOS = gruppeDTO.getDateien().get(0).getTagDTOs();
        }

        assertTrue(((GruppeDTO) userDTO.getBelegungUndRechte().keySet().toArray()[0])
                .getDateien().get(0).getDateigroesse() == 400);
        assertTrue(((GruppeDTO) userDTO.getBelegungUndRechte().keySet().toArray()[0])
                .getDateien().get(0).getDateityp().equals("gae"));
        assertTrue(((GruppeDTO) userDTO.getBelegungUndRechte().keySet().toArray()[0])
                .getDateien().get(0).getKategorie().equals("gaee"));

        assertTrue(((GruppeDTO) userDTO.getBelegungUndRechte().keySet().toArray()[0])
                .getDateien().get(0).getVeroeffentlichungsdatum().equals(newVeroeffentlichungsDatum));
        assertTrue(tagDTOS.get(0).getText().equals("gae1"));
        assertTrue(tagDTOS.get(1).getText().equals("gae2"));
    }

    @Test
    @SuppressWarnings("checkstyle:magicnumber")
    public void updateTwiceDateiTest() throws SQLException {
        ArrayList<TagDTO> newTags = new ArrayList<TagDTO>();
        TagDTO tag1 = new TagDTO("gae1");
        TagDTO tag2 = new TagDTO("gae2");
        TagDTO tag3 = new TagDTO("gae3");
        DateiDTO newDatei;
        newTags.add(tag1);
        newTags.add(tag2);

        LocalDate newVeroeffentlichungsDatum = LocalDate.of(2020, 3, 10);
        newDatei = new DateiDTO(datei.getId(), "gaedata",
                user, newTags, LocalDate.now(), newVeroeffentlichungsDatum, 400, "fish", gruppe, "gaee");

        repository.saveDatei(newDatei);
        newTags.add(tag3);
        newDatei.setDateigroesse(500);
        newDatei.setDateityp("new");
        newDatei.setKategorie("new");
        newDatei.setTagDTOs(newTags);
        newVeroeffentlichungsDatum = LocalDate.of(2020, 3, 11);
        newDatei.setVeroeffentlichungsdatum(newVeroeffentlichungsDatum);
        repository.saveDatei(newDatei);

        UserDTO userDTO = repository.findUserByKeycloaknameEager("gae");
        List<TagDTO> tagDTOS = new ArrayList<>();
        for (GruppeDTO gruppeDTO : userDTO.getBelegungUndRechte().keySet()) {
            tagDTOS = gruppeDTO.getDateien().get(0).getTagDTOs();
        }

        assertTrue(((GruppeDTO) userDTO.getBelegungUndRechte().keySet().toArray()[0])
                .getDateien().get(0).getDateigroesse() == 500);
        assertTrue(((GruppeDTO) userDTO.getBelegungUndRechte().keySet().toArray()[0])
                .getDateien().get(0).getDateityp().equals("gae"));
        assertTrue(((GruppeDTO) userDTO.getBelegungUndRechte().keySet().toArray()[0])
                .getDateien().get(0).getKategorie().equals("new"));
        assertTrue(((GruppeDTO) userDTO.getBelegungUndRechte().keySet().toArray()[0])
                .getDateien().get(0).getVeroeffentlichungsdatum().equals(newVeroeffentlichungsDatum));
        assertTrue(tagDTOS.get(0).getText().equals("gae1"));
        assertTrue(tagDTOS.get(1).getText().equals("gae2"));
        assertTrue(tagDTOS.get(2).getText().equals("gae3"));
    }

    @Test
    @SuppressWarnings("checkstyle:magicnumber")
    public void deleteTagnutzungByDateiTest() throws SQLException {

        repository.deleteTagRelationsByDateiId(datei.getId());
        assertFalse(repository.doTagsExistByDateiId(datei.getId()));

    }

    @Test
    public void deleteUserByIdTest() throws SQLException {
        repository.deleteUserByUserDTO(user);

        UserDTO shouldBeNull = repository.findUserByIdLAZY(user.getId());

        assertTrue(shouldBeNull == null);
    }

    @Test
    public void deleteGruppeByGruppeIdTest() throws SQLException {
        repository.deleteGroupByGroupDTO(gruppe);

        assertTrue(repository.findGruppeByGruppeIdEager(gruppe.getId()) == null);
        assertTrue(repository.findAllUserByGruppeId(gruppe.getId()).isEmpty());
        assertTrue(repository.findAllDateiByGruppeId(gruppe.getId()).isEmpty());
    }

    @Test
    public void deleteGruppenbelegungByUserTest() throws SQLException {
        long userId = user.getId();

        repository.deleteUserGroupRelationByUserId(userId);

        assertFalse(repository.doGroupRelationsExistByUserId(userId));
    }

    @Test
    public void deleteGruppenbelegungByGruppeTest() throws SQLException {
        long gruppeId = gruppe.getId();

        repository.deleteUserGroupRelationByGroupId(gruppeId);

        assertFalse(repository.doGroupRelationsExistByGruppeId(gruppeId));
    }

    @Test
    public void deleteGruppenbelegungByUserDTOandGruppeDTOTest() throws SQLException {
        repository.deleteUserGroupRelationByUserDTOAndGruppeDTO(user, gruppe);

        UserDTO loadedUser = repository.findUserByKeycloaknameEager(user.getKeycloakname());

        assertTrue(loadedUser.getBelegungUndRechte().keySet().isEmpty());
    }

    @SuppressWarnings("checkstyle:MagicNumber")
    @Ignore
    @Test
    public void add1UsersWith20GroupsWith100FilesWith10TagsEachAndLoad() throws SQLException {
        ArrayList<UserDTO> userDTOs =
                generateXUsersWithYGroupsWithZFilesWithATags(1, 20, 100, 10);

        LocalTime timeBeforeSave = LocalTime.now();
        System.out.println("Test started at: " + timeBeforeSave);

        for (int userNumber = 0; userNumber < 1; userNumber++) {
            repository.saveUser(userDTOs.get(userNumber));
            for (GruppeDTO gruppeDTO : userDTOs.get(userNumber).getBelegungUndRechte().keySet()) {
                for (DateiDTO dateiDTO: gruppeDTO.getDateien()) {
                    repository.saveDatei(dateiDTO);
                }
            }
            System.out.println(LocalTime.now() + " User #" + userNumber + " inserted!");
        }

        LocalTime timeAfterEverything = LocalTime.now();
        Duration duration = Duration.between(timeBeforeSave, timeAfterEverything);

        System.out.println(timeAfterEverything + " Everything saved!");
        System.out.println();
        System.out.println("This took " + duration.getSeconds() + " seconds.");

        timeBeforeSave = LocalTime.now();
        System.out.println(timeBeforeSave + " Loading Started!");
        repository.findUserByKeycloaknameEager(userDTOs.get(0).getKeycloakname());

        timeAfterEverything = LocalTime.now();
        duration = Duration.between(timeBeforeSave, timeAfterEverything);
        System.out.println(timeAfterEverything + " Loading done!");
        System.out.println();
        System.out.println("This took " + duration.getSeconds() + " seconds.");
    }

    @SuppressWarnings("checkstyle:MagicNumber")
    @Ignore
    @Test
    public void add10UsersWith10GroupsWith50FilesWith20TagsEachAndLoad() throws SQLException {
        ArrayList<UserDTO> userDTOs =
                generateXUsersWithYGroupsWithZFilesWithATags(10, 10, 50, 20);

        LocalTime timeBeforeSave = LocalTime.now();
        System.out.println("Test started at: " + timeBeforeSave);

        for (int userNumber = 0; userNumber < 10; userNumber++) {
            repository.saveUser(userDTOs.get(userNumber));
            for (GruppeDTO gruppeDTO : userDTOs.get(userNumber).getBelegungUndRechte().keySet()) {
                for (DateiDTO dateiDTO: gruppeDTO.getDateien()) {
                    repository.saveDatei(dateiDTO);
                }
            }
            System.out.println(LocalTime.now() + " User #" + userNumber + " inserted!");
        }

        LocalTime timeAfterEverything = LocalTime.now();
        Duration duration = Duration.between(timeBeforeSave, timeAfterEverything);

        System.out.println(timeAfterEverything + " Everything saved!");
        System.out.println();
        System.out.println("This took " + duration.getSeconds() + " seconds.");

        timeBeforeSave = LocalTime.now();
        System.out.println(timeBeforeSave + " Loading Started!");
        for (int userNumber = 0; userNumber < 10; userNumber++) {
            repository.findUserByKeycloaknameEager(userDTOs.get(userNumber).getKeycloakname());
        }

        timeAfterEverything = LocalTime.now();
        duration = Duration.between(timeBeforeSave, timeAfterEverything);
        System.out.println(timeAfterEverything + " Loading done!");
        System.out.println();
        System.out.println("This took " + duration.getSeconds() + " seconds.");
    }

    @SuppressWarnings("checkstyle:MagicNumber")
    @Ignore
    @Test
    public void add1UsersWith20GroupsWith100FilesWith1TagsEachAndLoad() throws SQLException {
        ArrayList<UserDTO> userDTOs =
                generateXUsersWithYGroupsWithZFilesWithATags(1, 20, 100, 1);

        LocalTime timeBeforeSave = LocalTime.now();
        System.out.println("Test started at: " + timeBeforeSave);

        for (int userNumber = 0; userNumber < 1; userNumber++) {
            repository.saveUser(userDTOs.get(userNumber));
            for (GruppeDTO gruppeDTO : userDTOs.get(userNumber).getBelegungUndRechte().keySet()) {
                for (DateiDTO dateiDTO : gruppeDTO.getDateien()) {
                    repository.saveDatei(dateiDTO);
                }
            }
            System.out.println(LocalTime.now() + " User #" + userNumber + " inserted!");
        }

        LocalTime timeAfterEverything = LocalTime.now();
        Duration duration = Duration.between(timeBeforeSave, timeAfterEverything);

        System.out.println(timeAfterEverything + " Everything saved!");
        System.out.println();
        System.out.println("This took " + duration.getSeconds() + " seconds.");

        timeBeforeSave = LocalTime.now();
        System.out.println(timeBeforeSave + " Loading Started!");
        repository.findUserByKeycloaknameEager(userDTOs.get(0).getKeycloakname());

        timeAfterEverything = LocalTime.now();
        duration = Duration.between(timeBeforeSave, timeAfterEverything);
        System.out.println(timeAfterEverything + " Loading done!");
        System.out.println();
        System.out.println("This took " + duration.getSeconds() + " seconds.");
    }

    @SuppressWarnings("checkstyle:MagicNumber")
    @Ignore
    @Test
    public void load100FilesWith3TagsEach() throws SQLException {
        ArrayList<UserDTO> userDTOs =
                generateXUsersWithYGroupsWithZFilesWithATags(1, 1, 500, 1);

        LocalTime timeBeforeSave = LocalTime.now();
        System.out.println("Test started at: " + timeBeforeSave);

        for (int userNumber = 0; userNumber < 1; userNumber++) {
            repository.saveUser(userDTOs.get(userNumber));
            for (GruppeDTO gruppeDTO : userDTOs.get(userNumber).getBelegungUndRechte().keySet()) {
                for (DateiDTO dateiDTO : gruppeDTO.getDateien()) {
                    repository.saveDatei(dateiDTO);
                }
            }
            System.out.println(LocalTime.now() + " User #" + userNumber + " inserted!");
        }

        LocalTime timeAfterEverything = LocalTime.now();
        Duration duration = Duration.between(timeBeforeSave, timeAfterEverything);

        System.out.println(timeAfterEverything + " Everything saved!");
        System.out.println();
        System.out.println("This took " + duration.getSeconds() + " seconds.");

        timeBeforeSave = LocalTime.now();
        System.out.println(timeBeforeSave + " Loading Started!");
        repository.findAllDateiByGruppeId(
                ((GruppeDTO) userDTOs.get(0).getBelegungUndRechte().keySet().toArray()[0]).getId());

        timeAfterEverything = LocalTime.now();
        duration = Duration.between(timeBeforeSave, timeAfterEverything);
        System.out.println(timeAfterEverything + " Loading done!");
        System.out.println();
        System.out.println("This took " + duration.getSeconds() + " seconds.");
    }
}
