package de.hhu.propra2.material2.mops.Database;

import de.hhu.propra2.material2.mops.Database.DTOs.DateiDTO;
import de.hhu.propra2.material2.mops.Database.DTOs.GruppeDTO;
import de.hhu.propra2.material2.mops.Database.DTOs.TagDTO;
import de.hhu.propra2.material2.mops.Database.DTOs.UserDTO;
import de.hhu.propra2.material2.mops.Material2Application;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;


@SpringBootTest(classes = Material2Application.class)
public class RepositoryTest {

    GruppeDTO gruppe;
    UserDTO user;
    TagDTO tag;
    ArrayList<TagDTO> tags;
    DateiDTO datei;

    @BeforeEach
    public void preparation() throws SQLException {
        gruppe = new GruppeDTO(99999999, "gruppe", "this is a description", null);
        HashMap<GruppeDTO, Boolean> berechtigung = new HashMap<GruppeDTO, Boolean>();
        berechtigung.put(gruppe, true);
        user = new UserDTO(999999, "Why are you gae?", "You are gae",
                "gae", berechtigung);
        tag = new TagDTO("gae");
        tags = new ArrayList<TagDTO>();
        tags.add(tag);
        datei = new DateiDTO("gaedata", "/materialsammlung/gaedata/",
                user, tags, LocalDate.now(), LocalDate.now(), 200, "gae", gruppe, "gae");

        Repository.saveUser(user);
        Repository.saveDatei(datei);
    }


    @Test
    public void LoadUserTest() throws SQLException {
        UserDTO userDTO = Repository.findUserByKeycloakname("gae");

        assertTrue(userDTO.getVorname().equals("Why are you gae?"));
        assertTrue(userDTO.getNachname().equals("You are gae"));
        assertTrue(userDTO.getId() == 999999);
        for (GruppeDTO gruppeDTO:userDTO.getBelegungUndRechte().keySet()) {
            assertTrue(userDTO.getBelegungUndRechte().get(gruppeDTO));
        }
    }

    @Test
    public void updateDateiTest() throws SQLException {
        ArrayList<TagDTO> newTags = new ArrayList<TagDTO>();
        TagDTO tag1 = new TagDTO("gae1");
        TagDTO tag2= new TagDTO("gae2");
        DateiDTO newDatei;

        newTags.add(tag1);
        newTags.add(tag2);

        newDatei = new DateiDTO(101, "gaedata", "/materialsammlung/gaedata/",
                user, newTags, LocalDate.now(), LocalDate.now(), 300, "gae", gruppe, "gae");


        Repository.saveDatei(newDatei);

        UserDTO userDTO = Repository.findUserByKeycloakname("gae");
        List<TagDTO> tagDTOS = new ArrayList<>();
        for (GruppeDTO gruppeDTO : userDTO.getBelegungUndRechte().keySet()) {
            tagDTOS = gruppeDTO.getDateien().get(0).getTagDTOs();
        }

        assertTrue(tagDTOS.get(0).getText().equals("gae1"));
        assertTrue(tagDTOS.get(1).getText().equals("gae2"));
    }

    @Test
    public void updateTwiceDateiTest() throws SQLException {
        ArrayList<TagDTO> newTags = new ArrayList<TagDTO>();
        TagDTO tag1 = new TagDTO("gae1");
        TagDTO tag2= new TagDTO("gae2");
        DateiDTO newDatei;

        newTags.add(tag1);
        newTags.add(tag2);

        newDatei = new DateiDTO(101, "gaedata", "/materialsammlung/gaedata/",
                user, newTags, LocalDate.now(), LocalDate.now(), 300, "gae", gruppe, "gae");

        Repository.saveDatei(newDatei);
        Repository.saveDatei(newDatei);

        UserDTO userDTO = Repository.findUserByKeycloakname("gae");
        List<TagDTO> tagDTOS = new ArrayList<>();
        for (GruppeDTO gruppeDTO : userDTO.getBelegungUndRechte().keySet()) {
            tagDTOS = gruppeDTO.getDateien().get(0).getTagDTOs();
        }

        assertTrue(tagDTOS.get(0).getText().equals("gae1"));
        assertTrue(tagDTOS.get(1).getText().equals("gae2"));
    }

    @Test
    public void deleteTagnutzungByDatei() throws SQLException {

    }

}
