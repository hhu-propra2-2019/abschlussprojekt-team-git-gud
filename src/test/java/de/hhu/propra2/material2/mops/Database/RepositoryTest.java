package de.hhu.propra2.material2.mops.Database;

import de.hhu.propra2.material2.mops.Database.DTOs.DateiDTO;
import de.hhu.propra2.material2.mops.Database.DTOs.GruppeDTO;
import de.hhu.propra2.material2.mops.Database.DTOs.TagDTO;
import de.hhu.propra2.material2.mops.Database.DTOs.UserDTO;
import de.hhu.propra2.material2.mops.Material2Application;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

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

        datei = new DateiDTO("gaedata", user, tags, LocalDate.now(),
                LocalDate.now(), 200, "gae", gruppe, "gae");
        gruppe.getDateien().add(datei);
    }

    @BeforeEach
    public void preparation() throws SQLException {
        repository.saveUser(user);
        datei.setId(repository.saveDatei(datei));
    }

    @AfterEach
    public void deletionOfRemainingStuff() throws SQLException {
        repository.deleteUserByUserDTO(user);
        repository.deleteGroupByGroupDTO(gruppe);
    }

    @Test
    @SuppressWarnings("checkstyle:magicnumber")
    public void loadUserTest() throws SQLException {
        UserDTO userDTO = repository.findUserByKeycloakname("gae");

        assertTrue(userDTO.getVorname().equals("Why are you gae?"));
        assertTrue(userDTO.getNachname().equals("You are gae"));
        assertTrue(userDTO.getId() == 999999);
        for (GruppeDTO gruppeDTO:userDTO.getBelegungUndRechte().keySet()) {
            assertTrue(userDTO.getBelegungUndRechte().get(gruppeDTO));
        }
    }

    @Test
    @SuppressWarnings("checkstyle:magicnumber")
    public void loadGruppeTest() throws SQLException {
        GruppeDTO gruppeDto = repository.findGruppeByGruppeId(99999999);

        assertTrue(gruppeDto.getName().equals("gruppe"));
        assertTrue(gruppeDto.getDescription().equals("this is a description"));
    }

    @Test
    @SuppressWarnings("checkstyle:magicnumber")
    public void loadDateiByGruppeTest() throws SQLException {
        List<DateiDTO> dateien = gruppe.getDateien();

        DateiDTO datei = dateien.get(0);

        assertTrue(datei.getName().equals("gaedata"));
        assertTrue(datei.getDateityp().equals("gae"));
        assertTrue(datei.getKategorie().equals("gae"));

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

        LocalDate newVeroeffentlichungsDatum = LocalDate.now();
        System.out.println(newVeroeffentlichungsDatum);
        newDatei = new DateiDTO(datei.getId(), "gaedata",
                user, newTags, LocalDate.now(), newVeroeffentlichungsDatum, 400, "fish", gruppe, "gaee");


        repository.saveDatei(newDatei);

        UserDTO userDTO = repository.findUserByKeycloakname("gae");
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
        System.out.println(newVeroeffentlichungsDatum);
        System.out.println(((GruppeDTO) userDTO.getBelegungUndRechte().keySet().toArray()[0]).getDateien().get(0).getVeroeffentlichungsdatum());

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
        TagDTO tag3 = new TagDTO("gae2");
        DateiDTO newDatei;

        newTags.add(tag1);
        newTags.add(tag2);

        LocalDate newVeroeffentlichungsDatum = LocalDate.now();
        newDatei = new DateiDTO(datei.getId(), "gaedata",
                user, newTags, LocalDate.now(), newVeroeffentlichungsDatum, 400, "fish", gruppe, "gaee");

        repository.saveDatei(newDatei);
        newTags.add(tag3);
        newDatei.setDateigroesse(500);
        newDatei.setDateityp("new");
        newDatei.setKategorie("new");
        newDatei.setTagDTOs(newTags);
        newVeroeffentlichungsDatum = LocalDate.now();
        newDatei.setUploaddatum(newVeroeffentlichungsDatum);
        repository.saveDatei(newDatei);

        UserDTO userDTO = repository.findUserByKeycloakname("gae");
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
        assertTrue(tagDTOS.get(1).getText().equals("gae3"));
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
        assertTrue(repository.findAllDateiByGruppeId(gruppe.getId()).isEmpty());
    }

    @Test
    public void deleteGruppenbelegungByUserTest() throws SQLException {
        long userId = user.getId();

        repository.deleteUserGroupRelationByUserId(userId);

        assertFalse(repository.getUserGroupRelationByUserId(userId));
    }

    @Test
    public void deleteGruppenbelegungByGruppeTest() throws SQLException {
        long gruppeId = gruppe.getId();

        repository.deleteUserGroupRelationByGroupId(gruppeId);

        assertFalse(repository.getUserGroupRelationByGroupId(gruppeId));
    }

    @Test
    public void deleteGruppenbelegungByUserDTOandGruppeDTOTest() throws SQLException {
        repository.deleteUserGroupRelationByUserDTOAndGruppeDTO(user, gruppe);

        UserDTO loadedUser = repository.findUserByKeycloakname(user.getKeycloakname());

        assertTrue(loadedUser.getBelegungUndRechte().keySet().isEmpty());
    }

}
