package de.hhu.propra2.material2.mops.web.dto;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
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
import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.sameInstance;

@ExtendWith(MockitoExtension.class)
public class WebDTOServiceTest {

    private String jsonExample;
    @Autowired
    private RestTemplate serviceAccountRestTemplate;

    @BeforeEach
    private void setup() throws IOException {
        String test  = System.getProperty("user.dir");
        //jsonExample = new String(Files.readAllBytes(Paths.get("example.json")));
        System.out.println(test);
    }

    @Test
    public void methodGeneratesTheRightObject() {
        Mockito.when(serviceAccountRestTemplate.getForEntity("http://localhost:8080/gruppe2//api/updateGroups/0",
                String.class)).thenReturn(new ResponseEntity(jsonExample, HttpStatus.OK));
    }
}
