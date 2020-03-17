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

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;


@SpringBootTest(classes = Material2Application.class)
public final class RepositoryTest {

    @Autowired
    private Repository repository;
    private GruppeDTO gruppe;
    private UserDTO user;
    private TagDTO tag;
    private ArrayList<TagDTO> tags;
    private DateiDTO datei;

    @SuppressWarnings("checkstyle:magicnumber")
    public RepositoryTest() {
        gruppe = new GruppeDTO(99999999, "gruppe", "this is a description", null);
        HashMap<GruppeDTO, Boolean> berechtigung = new HashMap<GruppeDTO, Boolean>();
        berechtigung.put(gruppe, true);
        user = new UserDTO(999999, "Why are you gae?", "You are gae",
                "gae", berechtigung);
        tag = new TagDTO("gae");
        tags = new ArrayList<TagDTO>();
        tags.add(tag);
        datei = new DateiDTO("gaedata",
                user, tags, LocalDate.now(), LocalDate.now(), 200, "gae", gruppe, "gae");
    }

    @BeforeEach
    public void preparation() throws SQLException {
        repository.saveUser(user);
        datei = new DateiDTO(repository.saveDatei(datei), datei.getName(),
            datei.getUploader(), datei.getTagDTOs(), datei.getUploaddatum(),
                datei.getVeroeffentlichungsdatum(), datei.getDateigroesse(),
                datei.getDateityp(), datei.getGruppe(), datei.getKategorie());
    }

    @AfterEach
    public void deletionOfRemainingStuff() throws SQLException {
        repository.deleteUserByUserId(user.getId());
        repository.deleteDateiByDateiId(datei.getId());
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
    public void updateDateiTest() throws SQLException {
        ArrayList<TagDTO> newTags = new ArrayList<TagDTO>();
        TagDTO tag1 = new TagDTO("gae1");
        TagDTO tag2 = new TagDTO("gae2");
        DateiDTO newDatei;

        newTags.add(tag1);
        newTags.add(tag2);

        newDatei = new DateiDTO(datei.getId(), "gaedata",
                user, newTags, LocalDate.now(), LocalDate.now(), 300, "gae", gruppe, "gae");


        repository.saveDatei(newDatei);

        UserDTO userDTO = repository.findUserByKeycloakname("gae");
        List<TagDTO> tagDTOS = new ArrayList<>();
        for (GruppeDTO gruppeDTO : userDTO.getBelegungUndRechte().keySet()) {
            tagDTOS = gruppeDTO.getDateien().get(0).getTagDTOs();
        }

        assertTrue(tagDTOS.get(0).getText().equals("gae1"));
        assertTrue(tagDTOS.get(1).getText().equals("gae2"));
    }

    @Test
    @SuppressWarnings("checkstyle:magicnumber")
    public void updateTwiceDateiTest() throws SQLException {
        ArrayList<TagDTO> newTags = new ArrayList<TagDTO>();
        TagDTO tag1 = new TagDTO("gae1");
        TagDTO tag2 = new TagDTO("gae2");
        DateiDTO newDatei;

        newTags.add(tag1);
        newTags.add(tag2);

        newDatei = new DateiDTO(datei.getId(), "gaedata",
                user, newTags, LocalDate.now(), LocalDate.now(), 300, "gae", gruppe, "gae");

        repository.saveDatei(newDatei);
        repository.saveDatei(newDatei);

        UserDTO userDTO = repository.findUserByKeycloakname("gae");
        List<TagDTO> tagDTOS = new ArrayList<>();
        for (GruppeDTO gruppeDTO : userDTO.getBelegungUndRechte().keySet()) {
            tagDTOS = gruppeDTO.getDateien().get(0).getTagDTOs();
        }

        assertTrue(tagDTOS.get(0).getText().equals("gae1"));
        assertTrue(tagDTOS.get(1).getText().equals("gae2"));
    }

    @Test
    @SuppressWarnings("checkstyle:magicnumber")
    public void deleteTagnutzungByDatei() throws SQLException {
        ArrayList<TagDTO> newTags = new ArrayList<TagDTO>();
        TagDTO tag1 = new TagDTO("gae1");
        TagDTO tag2 = new TagDTO("gae2");
        DateiDTO newDatei;

        newTags.add(tag1);
        newTags.add(tag2);

        newDatei = new DateiDTO(101, "gaedata",
                user, newTags, LocalDate.now(), LocalDate.now(), 300, "gae", gruppe, "gae");

        repository.saveDatei(newDatei);
        repository.saveDatei(newDatei);

        repository.deleteTagRelationsByDateiId(101);
        assertFalse(repository.getTagRelationByDateiId(101));

    }

}
