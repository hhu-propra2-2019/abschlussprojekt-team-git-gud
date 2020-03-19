package de.hhu.propra2.material2.mops.web.dto;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.keycloak.adapters.springsecurity.token.KeycloakAuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@Controller
public class TestController {
    /**
     * @param token
     * @param model
     * @return
     */
    @GetMapping("/yolo")
    @ResponseBody
    public String startseite(final KeycloakAuthenticationToken token,
                             final Model model) throws JsonProcessingException {

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
        System.out.println(mapper.writeValueAsString(sample));
        return mapper.writeValueAsString(sample);
    }
}
