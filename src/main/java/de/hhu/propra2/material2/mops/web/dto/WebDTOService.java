package de.hhu.propra2.material2.mops.web.dto;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.Objects;

public class WebDTOService {

    @Autowired
    RestTemplate serviceAccountRestTemplate;

    public WebDTOService(){

    }

    public void loadGroupWebDTOFromGroupManagementAPI(final int status) {
        try {
            var res = Arrays.asList(Objects.requireNonNull(serviceAccountRestTemplate.getForEntity("http://localhost:8090/gruppen2/api/updateGroups/{status}"
                    , GroupWebDTO[].class).getBody()));
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
