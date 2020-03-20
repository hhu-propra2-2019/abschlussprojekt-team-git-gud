package de.hhu.propra2.material2.mops.web.dto;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.client.RestTemplate;

public class WebDTOService {

    @Autowired
    private RestTemplate restTemplate;

    /**
     * @return
     */
    public JSONObject loadUpdatedGroupRequestMapperromGroupManagementAPI() throws UnirestException, JsonProcessingException {
       String response = Unirest.get("http://localhost:8080/yolo").asString().getBody();
       JSONObject updatedGroupRequestMapper = null;
       try{
           updatedGroupRequestMapper = new JSONObject(response);
       }catch (JSONException err){
           System.out.println(err.toString());
       }
       return updatedGroupRequestMapper;
    }
}
