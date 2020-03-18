package de.hhu.propra2.material2.mops.web;

import com.c4_soft.springaddons.test.security.context.support.WithMockKeycloackAuth;
import de.hhu.propra2.material2.mops.domain.services.ModelService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.keycloak.adapters.springboot.KeycloakSpringBootConfigResolver;
import org.keycloak.adapters.springsecurity.KeycloakSecurityComponents;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.content;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@WebMvcTest
@ComponentScan(basePackageClasses = { KeycloakSecurityComponents.class, KeycloakSpringBootConfigResolver.class })
public class SlicedControllerTest {

    @Autowired
    MockMvc mvc;

    @MockBean
    ModelService modelService;


    @BeforeEach
    void init(){
        //when(modelService.getAlleGruppenByUser()).thenReturn();
    }

    //Public User Access tests

    @Test
    @WithMockKeycloackAuth(name = "BennyGoodman", roles = "TESTER")
    void testStart_PublicUser() throws Exception {
        mvc.perform(get("/"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockKeycloackAuth(name = "Benny Goodman", roles = "TESTER")
    void testSuche_PublicUser() throws Exception {
        mvc.perform(get("/suche"))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockKeycloackAuth(name = "Benny Goodman", roles = "TESTER")
    void testUpload_PublicUser() throws Exception {
        mvc.perform(get("/upload"))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockKeycloackAuth(name = "Benny Goodman", roles = "TESTER")
    void testDateisicht_PublicUser() throws Exception {
        mvc.perform(get("/dateiSicht"))
                .andExpect(status().isForbidden());
    }

    //Student User Access tests

    @Test
    @WithMockKeycloackAuth(name = "Benny Goodman", roles = "studentin")
    void testStart_StudentUser() throws Exception {
        mvc.perform(get("/"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockKeycloackAuth(name = "Benny Goodman", roles = "studentin")
    void testSuche_StudentUser() throws Exception {
        mvc.perform(get("/suche"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockKeycloackAuth(name = "Benny Goodman", roles = "studentin")
    void testUpload_StudentUser() throws Exception {
        mvc.perform(get("/upload"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockKeycloackAuth(name = "Benny Goodman", roles = "studentin")
    void testDateisicht_StudentUser() throws Exception {
        mvc.perform(get("/dateiSicht"))
                .andExpect(status().isOk());
    }

    //Orga User Access tests

    @Test
    @WithMockKeycloackAuth(name = "Benny Goodman", roles = "orga")
    void testStart_OrgaUser() throws Exception {
        mvc.perform(get("/"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockKeycloackAuth(name = "Benny Goodman", roles = "orga")
    void testSuche_OrgaUser() throws Exception {
        mvc.perform(get("/suche"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockKeycloackAuth(name = "Benny Goodman", roles = "orga")
    void testUpload_OrgaUser() throws Exception {
        mvc.perform(get("/upload"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockKeycloackAuth(name = "Benny Goodman", roles = "orga")
    void testDateisicht_OrgaUser() throws Exception {
        mvc.perform(get("/dateiSicht"))
                .andExpect(status().isOk());
    }

    //Actuator User Access tests

    @Test
    @WithMockKeycloackAuth(name = "Benny Goodman", roles = "actuator")
    void testStart_OrgaUser() throws Exception {
        mvc.perform(get("/"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockKeycloackAuth(name = "Benny Goodman", roles = "actuator")
    void testSuche_OrgaUser() throws Exception {
        mvc.perform(get("/suche"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockKeycloackAuth(name = "Benny Goodman", roles = "actuator")
    void testUpload_OrgaUser() throws Exception {
        mvc.perform(get("/upload"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockKeycloackAuth(name = "Benny Goodman", roles = "actuator")
    void testDateisicht_OrgaUser() throws Exception {
        mvc.perform(get("/dateiSicht"))
                .andExpect(status().isOk());
    }
}
