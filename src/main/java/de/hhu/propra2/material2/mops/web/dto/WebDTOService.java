package de.hhu.propra2.material2.mops.web.dto;


import de.hhu.propra2.material2.mops.security.KeycloakConfig;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class WebDTOService {
    /**
     * @return
     */
    public ResponseEntity<String> loadUpdatedGroupRequestMapperromGroupManagementAPI() {
        KeycloakConfig config = new KeycloakConfig();
        RestTemplate serviceAccountRestTemplate = config.serviceAccountRestTemplate();
        final String URL = "http://localhost:8080/yolo";
        ResponseEntity<String> response = serviceAccountRestTemplate.getForEntity(URL, String.class);
        return response;
    }
}
