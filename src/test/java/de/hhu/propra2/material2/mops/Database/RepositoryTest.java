package de.hhu.propra2.material2.mops.Database;

import de.hhu.propra2.material2.mops.Database.DTOs.DateiDTO;
import de.hhu.propra2.material2.mops.Database.DTOs.GruppeDTO;
import de.hhu.propra2.material2.mops.Database.DTOs.TagDTO;
import de.hhu.propra2.material2.mops.Database.DTOs.UserDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

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
    public void preparation() {
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
    }


    @Test
    public void saveAndLoadUserTest() throws SQLException {
        Repository.saveUser(user);
        Repository.saveDatei(datei);

        UserDTO userDTO = Repository.findUserByKeycloakname("gae");

        assertTrue(userDTO.getVorname().equals("Why are you gae?"));
        assertTrue(userDTO.getNachname().equals("You are gae"));
        assertTrue(userDTO.getId() == 999999);
        for (GruppeDTO gruppeDTO:userDTO.getBelegungUndRechte().keySet()) {
            assertTrue(userDTO.getBelegungUndRechte().get(gruppeDTO));
        }
    }

}
