package de.hhu.propra2.material2.mops.web.dto;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.mashape.unirest.http.exceptions.UnirestException;
import netscape.javascript.JSObject;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

@ExtendWith(MockitoExtension.class)
public class WebDTOServiceTest {

    private String jsonFile;
    private WebDTOService webDTOService;

    @BeforeEach
    private void setup() throws JsonProcessingException {
        UserWebDTO donald = new UserWebDTO("789@yahoo.de", "Trump", "Donald", "0");
        UserWebDTO matvey = new UserWebDTO("123@yahoo.de", "Lorkish", "Matvey", "1");
        UserWebDTO torben = new UserWebDTO("456@yahoo.de", "Schmitz", "Torben", "2");
        UserWebDTO[] user1 = {matvey, torben};
        UserWebDTO[] user2 = {donald};

        Map<String, String> belegungenUndRechte1 = new HashMap<>();
        belegungenUndRechte1.put(matvey.getUserId(), "MEMBER");
        belegungenUndRechte1.put(torben.getUserId(), "MEMBER");
        Map<String, String> belegungenUndRechte2 = new HashMap<>();
        belegungenUndRechte2.put(donald.getUserId(), "ADMIN");

        GroupWebDTO gruppe1 = new GroupWebDTO("Toll",
                1,
                Arrays.asList(user1),
                1,
                belegungenUndRechte1,
                "TolleGruppe",
                "SIMPLE",
                "PUBLIC");
        GroupWebDTO gruppe2 = new GroupWebDTO("Nice",
                2,
                Arrays.asList(user2),
                2,
                belegungenUndRechte2,
                "DoofeGruppe",
                "LECTURE",
                "PRIVATE");
        GroupWebDTO[] gruppenArray = {gruppe1, gruppe2};

        UpdatedGroupRequestMapper sample = new UpdatedGroupRequestMapper(Arrays.asList(gruppenArray), 1);
        ObjectMapper mapper = new ObjectMapper();
        jsonFile = mapper.writeValueAsString(sample);

        webDTOService = new WebDTOService();
    }

    @Test
    public void methodGeneratesTheRightObject(){
        ResponseEntity<String> test = webDTOService.loadUpdatedGroupRequestMapperromGroupManagementAPI();
        assertThat(test.getStatusCode(), equalTo(HttpStatus.OK));
    }
}
