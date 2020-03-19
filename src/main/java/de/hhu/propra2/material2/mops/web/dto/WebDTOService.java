package de.hhu.propra2.material2.mops.web.dto;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.Objects;

public class WebDTOService {

    @Autowired
    private RestTemplate serviceAccountRestTemplate;

    public void loadGroupWebDTOFromGroupManagementAPI(final int status) {
        String state = String.valueOf(status);
        try {
            var res = Objects.requireNonNull(serviceAccountRestTemplate.
                    getForEntity("http://localhost:8090/gruppen2/api/updateGroups/{status}".replace("status", state),
                            UpdatedGroupRequestMapper.class).getBody());
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
