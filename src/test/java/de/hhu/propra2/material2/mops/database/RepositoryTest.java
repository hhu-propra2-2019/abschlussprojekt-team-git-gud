package de.hhu.propra2.material2.mops.database;

import de.hhu.propra2.material2.mops.Material2Application;
import de.hhu.propra2.material2.mops.database.DTOs.DateiDTO;
import de.hhu.propra2.material2.mops.database.DTOs.GruppeDTO;
import de.hhu.propra2.material2.mops.database.DTOs.TagDTO;
import de.hhu.propra2.material2.mops.database.DTOs.UserDTO;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import static org.junit.Assert.assertFalse;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;


@SpringBootTest(classes = Material2Application.class)
final class RepositoryTest {

    private final Repository repository;
    private final GruppeDTO gruppe;
    private final UserDTO user;
    private final DateiDTO datei;

    @SuppressWarnings("checkstyle:magicnumber")
    @Autowired
    RepositoryTest(final Repository repositoryArg) {
        repository = repositoryArg;

        TagDTO tag = new TagDTO("gae");
        LinkedList<TagDTO> tags = new LinkedList<>();
        tags.add(tag);

        LinkedList<DateiDTO> dateien = new LinkedList<>();
        gruppe = new GruppeDTO(99999999, "gruppe", "this is a description", dateien, repositoryArg);
        HashMap<GruppeDTO, Boolean> berechtigung = new HashMap<>();
        berechtigung.put(gruppe, true);

        user = new UserDTO(999999, "Why are you gae?", "You are gae",
                "gae", berechtigung);

        datei = new DateiDTO("gaedata", user, tags, LocalDate.of(2020, 3, 1),
                LocalDate.now(), 200, "gae", gruppe, "gae");
        dateien.add(datei);
    }

    @BeforeEach
    void preparation() throws SQLException {
        repository.saveUser(user);
        datei.setId(repository.saveDatei(datei));
    }

    @AfterEach
    void deleteAll() throws SQLException {
        repository.deleteAll();
    }

    @Test
    @SuppressWarnings("checkstyle:magicnumber")
    void loadUserTest() throws SQLException {
        UserDTO userDTO = repository.findUserByKeycloaknameEager("gae");

        assertEquals("Why are you gae?", userDTO.getVorname());
        assertEquals("You are gae", userDTO.getNachname());
        assertEquals(999999, userDTO.getId());
        for (GruppeDTO gruppeDTO : userDTO.getBelegungUndRechte().keySet()) {
            assertTrue(userDTO.getBelegungUndRechte().get(gruppeDTO));
        }
    }

    @Test
    @SuppressWarnings("checkstyle:magicnumber")
    void loadGruppeTest() throws SQLException {
        GruppeDTO gruppeDto = repository.findGruppeByGruppeIdEager(99999999);

        assertEquals("gruppe", gruppeDto.getName());
        assertEquals("this is a description", gruppeDto.getDescription());
    }

    @Test
    @SuppressWarnings("checkstyle:magicnumber")
    void updateDateiTest() throws SQLException {
        LinkedList<TagDTO> newTags = new LinkedList<>();
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
        List<TagDTO> tagDTOS = new LinkedList<>();
        for (GruppeDTO gruppeDTO : userDTO.getBelegungUndRechte().keySet()) {
            tagDTOS = gruppeDTO.getDateien().get(0).getTagDTOs();
        }

        assertEquals(400, ((GruppeDTO) userDTO.getBelegungUndRechte().keySet().toArray()[0])
                .getDateien().get(0).getDateigroesse());
        assertEquals("gae", ((GruppeDTO) userDTO.getBelegungUndRechte().keySet().toArray()[0])
                .getDateien().get(0).getDateityp());
        assertEquals("gaee", ((GruppeDTO) userDTO.getBelegungUndRechte().keySet().toArray()[0])
                .getDateien().get(0).getKategorie());

        assertEquals(((GruppeDTO) userDTO.getBelegungUndRechte().keySet().toArray()[0])
                .getDateien().get(0).getVeroeffentlichungsdatum(), newVeroeffentlichungsDatum);
        assertEquals("gae1", tagDTOS.get(0).getText());
        assertEquals("gae2", tagDTOS.get(1).getText());
    }

    @Test
    @SuppressWarnings("checkstyle:magicnumber")
    void updateTwiceDateiTest() throws SQLException {
        LinkedList<TagDTO> newTags = new LinkedList<>();
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
        List<TagDTO> tagDTOS = new LinkedList<>();
        for (GruppeDTO gruppeDTO : userDTO.getBelegungUndRechte().keySet()) {
            tagDTOS = gruppeDTO.getDateien().get(0).getTagDTOs();
        }

        assertEquals(500, ((GruppeDTO) userDTO.getBelegungUndRechte().keySet().toArray()[0])
                .getDateien().get(0).getDateigroesse());
        assertEquals("gae", ((GruppeDTO) userDTO.getBelegungUndRechte().keySet().toArray()[0])
                .getDateien().get(0).getDateityp());
        assertEquals("new", ((GruppeDTO) userDTO.getBelegungUndRechte().keySet().toArray()[0])
                .getDateien().get(0).getKategorie());
        assertEquals(((GruppeDTO) userDTO.getBelegungUndRechte().keySet().toArray()[0])
                .getDateien().get(0).getVeroeffentlichungsdatum(), newVeroeffentlichungsDatum);
        assertEquals("gae1", tagDTOS.get(0).getText());
        assertEquals("gae2", tagDTOS.get(1).getText());
        assertEquals("gae3", tagDTOS.get(2).getText());
    }

    @Test
    @SuppressWarnings("checkstyle:magicnumber")
    void deleteTagnutzungByDateiTest() throws SQLException {

        repository.deleteTagRelationsByDateiId(datei.getId());
        assertFalse(repository.doTagsExistByDateiId(datei.getId()));

    }

    @Test
    void deleteUserByIdTest() throws SQLException {
        repository.deleteUserByUserDTO(user);

        UserDTO shouldBeNull = repository.findUserByIdLAZY(user.getId());

        assertNull(shouldBeNull);
    }

    @Test
    void deleteGruppeByGruppeIdTest() throws SQLException {
        repository.deleteGroupByGroupDTO(gruppe);

        assertNull(repository.findGruppeByGruppeIdEager(gruppe.getId()));
        assertTrue(repository.findAllUserByGruppeId(gruppe.getId()).isEmpty());
        assertTrue(repository.findAllDateiByGruppeId(gruppe.getId()).isEmpty());
    }

    @Test
    void deleteGruppenbelegungByUserTest() throws SQLException {
        long userId = user.getId();

        repository.deleteUserGroupRelationByUserId(userId);

        assertFalse(repository.doGroupRelationsExistByUserId(userId));
    }

    @Test
    void deleteGruppenbelegungByGruppeTest() throws SQLException {
        long gruppeId = gruppe.getId();

        repository.deleteUserGroupRelationByGroupId(gruppeId);

        assertFalse(repository.doGroupRelationsExistByGruppeId(gruppeId));
    }

    @Test
    void deleteGruppenbelegungByUserDTOandGruppeDTOTest() throws SQLException {
        repository.deleteUserGroupRelationByUserDTOAndGruppeDTO(user, gruppe);

        UserDTO loadedUser = repository.findUserByKeycloaknameEager(user.getKeycloakname());

        assertTrue(loadedUser.getBelegungUndRechte().keySet().isEmpty());
    }
}
