package de.hhu.propra2.material2.mops.web.dto;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.hhu.propra2.material2.mops.database.DTOs.GruppeDTO;
import de.hhu.propra2.material2.mops.database.DTOs.UserDTO;
import de.hhu.propra2.material2.mops.database.Repository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

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

    }

}
