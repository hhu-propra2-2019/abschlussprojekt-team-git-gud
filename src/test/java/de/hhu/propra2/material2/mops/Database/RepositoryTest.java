package de.hhu.propra2.material2.mops.Database;

import de.hhu.propra2.material2.mops.Database.DTOs.DateiDTO;
import de.hhu.propra2.material2.mops.Database.DTOs.GruppeDTO;
import de.hhu.propra2.material2.mops.Database.DTOs.TagDTO;
import de.hhu.propra2.material2.mops.Database.DTOs.UserDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertTrue;

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
    public void saveAndLoadUserTest() throws SQLException {
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

        newTags.add(tag1);
        newTags.add(tag2);

        datei = new DateiDTO("gaedata", "/materialsammlung/gaedata/",
                user, newTags, LocalDate.now(), LocalDate.now(), 300, "gae", gruppe, "gae");

        UserDTO userDTO = Repository.findUserByKeycloakname("gae");

        assertTrue(userDTO.getVorname().equals("Why are you gae?"));
        assertTrue(userDTO.getNachname().equals("You are gae"));
        assertTrue(userDTO.getId() == 999999);
        for (GruppeDTO gruppeDTO:userDTO.getBelegungUndRechte().keySet()) {
            assertTrue(userDTO.getBelegungUndRechte().get(gruppeDTO));
        }

    }


    @Test
    public void deleteTagnutzungByDatei() throws SQLException {

    }

}
