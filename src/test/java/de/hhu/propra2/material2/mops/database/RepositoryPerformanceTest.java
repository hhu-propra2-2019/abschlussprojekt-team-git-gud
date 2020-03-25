package de.hhu.propra2.material2.mops.database;

import de.hhu.propra2.material2.mops.Material2Application;
import de.hhu.propra2.material2.mops.database.DTOs.DateiDTO;
import de.hhu.propra2.material2.mops.database.DTOs.GruppeDTO;
import de.hhu.propra2.material2.mops.database.DTOs.TagDTO;
import de.hhu.propra2.material2.mops.database.DTOs.UserDTO;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

import java.sql.SQLException;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertTrue;

@Sql("/data.sql")
@SpringBootTest(classes = Material2Application.class)
public final class RepositoryPerformanceTest {

    private final Repository repository;

    @Autowired
    public RepositoryPerformanceTest(final Repository repositoryArg) {
        repository = repositoryArg;
    }


    private LinkedList<UserDTO> generateXUsersWithYGroupsWithZFilesWithATags(final int userCount,
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
        return new LinkedList<UserDTO>(Arrays.asList(users));
    }

    private LinkedList<TagDTO> generateRandomTags(final int tagCount) {
        LinkedList<TagDTO> tagDTOs = new LinkedList<TagDTO>();
        for (int i = 0; i < tagCount; i++) {
            tagDTOs.add(new TagDTO(UUID.randomUUID().toString()));
        }
        return tagDTOs;
    }

    @SuppressWarnings("checkstyle:MagicNumber")
    private DateiDTO generateRandomDatei(final LinkedList<TagDTO> tagsArg, final GruppeDTO gruppeDTO) {
        return new DateiDTO(UUID.randomUUID().toString(),
                new UserDTO(-1, "User", "Deleted", "-", null),
                tagsArg, LocalDate.now(), LocalDate.now(),
                200, UUID.randomUUID().toString(),
                gruppeDTO, UUID.randomUUID().toString());
    }

    private GruppeDTO generateRandomGruppe() {
        return new GruppeDTO(UUID.randomUUID().getMostSignificantBits() & Long.MAX_VALUE,
                " ", " ", new LinkedList<DateiDTO>());
    }

    private UserDTO generateRandomUser() {
        return new UserDTO(UUID.randomUUID().getMostSignificantBits() & Long.MAX_VALUE,
                " ", " ", UUID.randomUUID().toString(), new HashMap<GruppeDTO, Boolean>());
    }

    @SuppressWarnings("checkstyle:MagicNumber")
    @Disabled
    @Test
    public void add1UsersWith20GroupsWith100FilesWith10TagsEachAndLoad() throws SQLException {
        LinkedList<UserDTO> userDTOs =
                generateXUsersWithYGroupsWithZFilesWithATags(1, 20, 100, 10);

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
    @Disabled
    @Test
    public void add10UsersWith10GroupsWith50FilesWith20TagsEachAndLoad() throws SQLException {
        LinkedList<UserDTO> userDTOs =
                generateXUsersWithYGroupsWithZFilesWithATags(10, 10, 50, 20);

        LocalTime timeBeforeSave = LocalTime.now();
        System.out.println("Test started at: " + timeBeforeSave);

        for (int userNumber = 0; userNumber < 10; userNumber++) {
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
    @Disabled
    @Test
    public void add1UsersWith20GroupsWith100FilesWith1TagsEachAndLoad() throws SQLException {
        LinkedList<UserDTO> userDTOs =
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
    @Disabled
    @Test
    public void load100FilesWith3TagsEach() throws SQLException {
        LinkedList<UserDTO> userDTOs =
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
        repository.findAllDateiByGruppeDTO(
                ((GruppeDTO) userDTOs.get(0).getBelegungUndRechte().keySet().toArray()[0]));

        timeAfterEverything = LocalTime.now();
        duration = Duration.between(timeBeforeSave, timeAfterEverything);
        System.out.println(timeAfterEverything + " Loading done!");
        System.out.println();
        System.out.println("This took " + duration.getSeconds() + " seconds.");
    }

    @SuppressWarnings("checkstyle:magicnumber")
    @Disabled
    @Test
    public void load1UserWith1GroupWith1100FilesWith3Tags() throws SQLException {
        LocalTime before = LocalTime.now();
        System.out.println(before + " Time test for 1 User 1 Group 1100 Files 3 Tags... Start!");
        UserDTO userDTO = repository.findUserByKeycloakname("_test_");
        LocalTime after = LocalTime.now();
        UserDTO userDTO2 = repository.findUserByKeycloakname("_test_");

        Duration timePassed = Duration.between(before, after);
        System.out.println(after + "Time passed to load the user without files: "
                + timePassed.getSeconds() + " seconds");

        before = LocalTime.now();
        System.out.println(before + " Loading (not cached) Files now...");
        GruppeDTO gruppeDTO = ((GruppeDTO) userDTO.getBelegungUndRechte().keySet().toArray()[0]);
        LinkedList<DateiDTO> dateiDTOs = (LinkedList<DateiDTO>) gruppeDTO.getDateien();
        after = LocalTime.now();
        timePassed = Duration.between(before, after);
        System.out.println(after + " Time passed to load the users files (not cached): "
                + timePassed.getSeconds() + " seconds.");

        before = LocalTime.now();
        System.out.println(before + " Loading cached (in Repository) Files now...");
        GruppeDTO gruppeDTO2 = ((GruppeDTO) userDTO2.getBelegungUndRechte().keySet().toArray()[0]);
        LinkedList<DateiDTO> dateiDTOs2 = (LinkedList<DateiDTO>) gruppeDTO2.getDateien();
        after = LocalTime.now();
        timePassed = Duration.between(before, after);
        System.out.println(after + " Time passed to load the users cached (in Repository) files: "
                + timePassed.getSeconds() + " seconds.");

        before = LocalTime.now();
        System.out.println(before + " Loading saved (in GroupDTO) Files now...");
        LinkedList<DateiDTO> dateiDTOs3 = (LinkedList<DateiDTO>) gruppeDTO.getDateien();
        after = LocalTime.now();
        timePassed = Duration.between(before, after);
        System.out.println(after + " Time passed to load the users saved (in GroupDTO) files: "
                + timePassed.getSeconds() + " seconds.");


        assertTrue(dateiDTOs.size() == 1100);
        assertTrue(dateiDTOs2.size() == 1100);
        assertTrue(dateiDTOs3.size() == 1100);
        System.out.println("Test complete!");
    }
}
