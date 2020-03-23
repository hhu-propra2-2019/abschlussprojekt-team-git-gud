package de.hhu.propra2.material2.mops.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import de.hhu.propra2.material2.mops.web.dto.UpdatedGroupRequestMapper;
import de.hhu.propra2.material2.mops.web.dto.WebDTOService;
import lombok.Getter;
import org.keycloak.adapters.springsecurity.token.KeycloakAuthenticationToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class MaterialRestController {

    @Autowired
    WebDTOService webDTOService;

    @GetMapping("keyCloak")
    public void getStringKeyCloak(){
        String test = webDTOService.loadUpdateWrapperKeyCloakConfig();
    }

    @GetMapping("update")
    public void getUpdate() throws JsonProcessingException {
        UpdatedGroupRequestMapper test = webDTOService.loadUpdateWrapper();
    }

}
