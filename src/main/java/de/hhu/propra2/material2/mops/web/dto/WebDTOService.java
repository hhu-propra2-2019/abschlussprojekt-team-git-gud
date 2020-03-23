package de.hhu.propra2.material2.mops.web.dto;


import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class WebDTOService {
    /**
     * @return
     */
    public String loadUpdatedGroupRequestMapperromGroupManagementAPI() {
        /**
         KeycloakConfig config = new KeycloakConfig();
         RestTemplate serviceAccountRestTemplate = config.serviceAccountRestTemplate();

         final String URL = "http://localhost:8080/yolo";
         ResponseEntity<String> response = serviceAccountRestTemplate.getForEntity(URL, String.class);
         return response;
         **/
        RestTemplate restTemplate = new RestTemplate();
        String ressource = "http://localhost:8080/yolo";
        String response = restTemplate.getForObject(ressource, String.class);
        return response;
    }
}
