package de.hhu.propra2.material2.mops.web.dto;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.client.RestTemplate;

public class WebDTOService {

    @Autowired
    private RestTemplate serviceAccountRestTemplate;

    /**
     * @param status
     * @param url
     * @return
     */
    public UpdatedGroupRequestMapper loadUpdatedGroupRequestMapperromGroupManagementAPI(final int status,
                                                                                        final String url) {

        UpdatedGroupRequestMapper updatedGroupRequestMapper = null;

        try {
            updatedGroupRequestMapper = serviceAccountRestTemplate.getForEntity(url,
                    UpdatedGroupRequestMapper.class).getBody();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return updatedGroupRequestMapper;
    }

    public static String getURL() {
        return "http://localhost:8090/gruppen2/api/updateGroups/{status}";
    }
}
