package de.hhu.propra2.material2.mops.web.dto;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.hhu.propra2.material2.mops.Material2Application;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import static org.hamcrest.Matchers.is;

import java.io.*;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.sameInstance;

@ExtendWith(MockitoExtension.class)
public class WebDTOServiceTest {

    @Mock
    private RestTemplate serviceAccountRestTemplate;

    private String jsonExample;
    private BufferedReader bufferedReader;
    private UpdatedGroupRequestMapper group;

    @BeforeEach
    private void setup() throws IOException {
        jsonExample = "";
        File file = new File("src/main/resources/example.json");
        bufferedReader = new BufferedReader(new FileReader(file));
        bufferedReader.lines().forEach(string -> jsonExample = jsonExample.concat(string));
        ObjectMapper mapper =  new ObjectMapper();
        group = mapper.readValue(file, UpdatedGroupRequestMapper.class);
    }

    @Test
    public void testConversionOfUserP() {
        Mockito.when(serviceAccountRestTemplate.getForEntity("http://localhost:8080/gruppe2//api/updateGroups/0",
                UpdatedGroupRequestMapper.class)).thenReturn(new ResponseEntity(group, HttpStatus.OK));

        UpdatedGroupRequestMapper updatedGroupRequestMapper = serviceAccountRestTemplate
                                .getForEntity("http://localhost:8080/gruppe2//api/updateGroups/0", UpdatedGroupRequestMapper.class)
                                .getBody();

    }

}
