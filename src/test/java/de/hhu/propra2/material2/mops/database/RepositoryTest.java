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
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(classes = Material2Application.class)
public final class RepositoryTest {

    private Repository repository;
    private GruppeDTO gruppe;
    private UserDTO user;
    private TagDTO tag;
    private LinkedList<TagDTO> tags = new LinkedList<TagDTO>();
    private DateiDTO datei;
    private LinkedList<DateiDTO> dateien = new LinkedList<DateiDTO>();
    private HashMap<GruppeDTO, Boolean> berechtigung = new HashMap<GruppeDTO, Boolean>();

    @SuppressWarnings("checkstyle:magicnumber")
    @Autowired
    public RepositoryTest(final Repository repositoryArg) {
        repository = repositoryArg;

        tag = new TagDTO("gae");
        tags.add(tag);

        gruppe = new GruppeDTO("99999999", "gruppe", "this is a description", dateien, repositoryArg);
        berechtigung.put(gruppe, true);

        user = new UserDTO(999999, "Why are you gae?", "You are gae",
                "gae", berechtigung);

        datei = new DateiDTO("gaedata", user, tags, LocalDate.of(2020, 3, 01),
                LocalDate.now(), 200, "gae", gruppe, "gae");
        dateien.add(datei);
    }

    @BeforeEach
    public void preparation() throws SQLException {
        repository.saveUser(user);
        datei.setId(repository.saveDatei(datei));
    }

    @AfterEach
    public void deleteAll() throws SQLException {
        repository.deleteAll();
    }

    @Test
    @SuppressWarnings("checkstyle:magicnumber")
    public void loadUserTest() throws SQLException {
        UserDTO userDTO = repository.findUserByKeycloakname("gae");

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
        GruppeDTO gruppeDto = repository.findGruppeByGruppeId(gruppe.getId());

        assertTrue(gruppeDto.getName().equals("gruppe"));
        assertTrue(gruppeDto.getDescription().equals("this is a description"));
    }

    @Test
    @SuppressWarnings("checkstyle:magicnumber")
    public void updateDateiTest() throws SQLException {
        LinkedList<TagDTO> newTags = new LinkedList<TagDTO>();
        TagDTO tag1 = new TagDTO("gae1");
        TagDTO tag2 = new TagDTO("gae2");
        DateiDTO newDatei;

        newTags.add(tag1);
        newTags.add(tag2);

        LocalDate newVeroeffentlichungsDatum = LocalDate.of(2020, 3, 10);
        newDatei = new DateiDTO(datei.getId(), "gaedata",
                user, newTags, LocalDate.now(), newVeroeffentlichungsDatum, 400, "fish", gruppe, "gaee");


        repository.saveDatei(newDatei);

        UserDTO userDTO = repository.findUserByKeycloakname("gae");
        List<TagDTO> tagDTOS = new LinkedList<>();
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
        LinkedList<TagDTO> newTags = new LinkedList<TagDTO>();
        TagDTO tag1 = new TagDTO("gae1");
        TagDTO tag2 = new TagDTO("gae2");
        TagDTO tag3 = new TagDTO("gae3");
        DateiDTO newDatei;
        newTags.add(tag1);
        newTags.add(tag2);

        LocalDate newVeroeffentlichungsDatum = LocalDate.of(2020, 3, 10);
        newDatei = new DateiDTO(datei.getId(), "gaedata",
                user, newTags, LocalDate.now(), newVeroeffentlichungsDatum, 400, "fish", gruppe, "gaee");

        newDatei.setId(repository.saveDatei(newDatei));
        newTags.add(tag3);
        newDatei.setDateigroesse(500);
        newDatei.setDateityp("new");
        newDatei.setKategorie("new");
        newDatei.setTagDTOs(newTags);
        newVeroeffentlichungsDatum = LocalDate.of(2020, 3, 11);
        newDatei.setVeroeffentlichungsdatum(newVeroeffentlichungsDatum);
        newDatei.setId(repository.saveDatei(newDatei));

        UserDTO userDTO = repository.findUserByKeycloakname("gae");
        List<TagDTO> tagDTOS = new LinkedList<>();
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

        assertTrue(repository.findGruppeByGruppeId(gruppe.getId()) == null);
        assertTrue(repository.findAllUserByGruppeId(gruppe.getId()).isEmpty());
        assertTrue(repository.findAllDateiByGruppeDTO(gruppe).isEmpty());
    }

    @Test
    public void deleteGruppenbelegungByUserTest() throws SQLException {
        long userId = user.getId();

        repository.deleteUserGroupRelationByUserId(userId);

        assertFalse(repository.doGroupRelationsExistByUserId(userId));
    }

    @Test
    public void deleteGruppenbelegungByGruppeTest() throws SQLException {
        String gruppeId = gruppe.getId();

        repository.deleteUserGroupRelationByGroupId(gruppeId);

        assertFalse(repository.doGroupRelationsExistByGruppeId(gruppeId));
    }

    @Test
    public void deleteGruppenbelegungByUserDTOandGruppeDTOTest() throws SQLException {
        repository.deleteUserGroupRelationByUserDTOAndGruppeDTO(user, gruppe);

        UserDTO loadedUser = repository.findUserByKeycloakname(user.getKeycloakname());

        assertTrue(loadedUser.getBelegungUndRechte().keySet().isEmpty());
    }
}
