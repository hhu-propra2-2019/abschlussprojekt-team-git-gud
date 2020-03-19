package de.hhu.propra2.material2.mops.web.dto;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;

public class WebDTOService {

    @Autowired
    RestTemplate serviceAccountRestTemplate;

    public UpdatedGroupRequestMapper loadUpdatedGroupRequestMapperromGroupManagementAPI(final int status) {
        UpdatedGroupRequestMapper updatedGroupRequestMapper = null;

        try {
            updatedGroupRequestMapper = serviceAccountRestTemplate.getForObject("http://localhost:8090/gruppen2/api/updateGroups/{status}",
                    UpdatedGroupRequestMapper.class, status);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return updatedGroupRequestMapper;
    }
}
