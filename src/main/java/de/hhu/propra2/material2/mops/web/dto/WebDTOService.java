package de.hhu.propra2.material2.mops.web.dto;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.hhu.propra2.material2.mops.security.KeycloakConfig;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class WebDTOService {
    /**
     * @return
     */
    public String loadUpdateWrapperKeyCloakConfig() {
         KeycloakConfig config = new KeycloakConfig();
         RestTemplate serviceAccountRestTemplate = config.serviceAccountRestTemplate();
         final String URL = "http://localhost:8080/yolo";
         String response = serviceAccountRestTemplate.getForEntity(URL, String.class).getBody();
         return response;
    }

    public UpdatedGroupRequestMapper loadUpdateWrapper() throws JsonProcessingException {
        RestTemplate restTemplate = new RestTemplate();
        String ressource = "http://localhost:8080/yolo";
        String response = restTemplate.getForObject(ressource, String.class);

        ObjectMapper mapper = new ObjectMapper();
        UpdatedGroupRequestMapper result = mapper.readValue(response, UpdatedGroupRequestMapper.class);
        return result;
    }
}
