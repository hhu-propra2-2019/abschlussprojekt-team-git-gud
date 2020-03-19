package de.hhu.propra2.material2.mops.web.dto;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;

import java.util.HashMap;
import java.util.Map;


public class WebDTOServiceTest {

    private String jsonFile;

    @BeforeEach
    private void setup() {
        UserWebDTO matvey = new UserWebDTO("123@yahoo.de", "Lorkish", "Matvey", 1);
        UserWebDTO torben = new UserWebDTO("456@yahoo.de", "Schmitz", "Torben", 2);
        UserWebDTO[] user = {matvey, torben};

        Map<Integer,String> belegungenUndRechte= new HashMap<>();
        belegungenUndRechte.put(matvey.getUserId(),"MEMBER");
        belegungenUndRechte.put(torben.getUserId(),"MEMBER");

        GroupWebDTO gruppe = new GroupWebDTO("Toll", 1,user,1,)
        JSONObject sample = new JSONObject();

    }

}
