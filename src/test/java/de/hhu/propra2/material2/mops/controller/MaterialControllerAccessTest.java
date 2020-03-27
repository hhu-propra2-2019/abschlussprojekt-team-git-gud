package de.hhu.propra2.material2.mops.controller;

import com.c4_soft.springaddons.test.security.context.support.WithMockKeycloackAuth;
import de.hhu.propra2.material2.mops.domain.models.Gruppe;
import de.hhu.propra2.material2.mops.domain.services.DeleteService;
import de.hhu.propra2.material2.mops.domain.services.MinIOService;
import de.hhu.propra2.material2.mops.domain.services.ModelService;
import de.hhu.propra2.material2.mops.domain.services.UpdateService;
import de.hhu.propra2.material2.mops.domain.services.UploadService;
import de.hhu.propra2.material2.mops.security.Account;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.keycloak.adapters.springboot.KeycloakSpringBootConfigResolver;
import org.keycloak.adapters.springsecurity.KeycloakSecurityComponents;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
@ComponentScan(basePackageClasses = {KeycloakSecurityComponents.class, KeycloakSpringBootConfigResolver.class})
class MaterialControllerAccessTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private ModelService modelService;

    @MockBean
    private MinIOService minIOService;

    @MockBean
    private UploadService uploadService;

    @MockBean
    private UpdateService updateService;

    @MockBean
    private DeleteService deleteService;

    /**
     * init for the tests.
     */
    @BeforeEach
    void init() {
        List<Gruppe> gruppen = new ArrayList<>();
        gruppen.add(new Gruppe(1, "ProPra", null));
        gruppen.add(new Gruppe(2, "RDB", null));
        Set<String> tags = new HashSet<>();
        tags.add("Vorlesung");
        tags.add("Übung");
        Set<String> uploader = new HashSet<>();
        uploader.add("Chris");
        uploader.add("Christian");
        uploader.add("Christiano Ronaldo");
        Set<String> dateiTypen = new HashSet<>();
        dateiTypen.add("XML");
        dateiTypen.add("JSON");
        when(modelService.getAlleGruppenByUser(any())).thenReturn(gruppen);
        when(modelService.getGruppeByUserAndGroupID(any(), any())).thenReturn(new Gruppe(2,
                "RDB", null));
        when(modelService.getAlleTagsByUser(any())).thenReturn(tags);
        when(modelService.getAlleUploaderByUser(any())).thenReturn(uploader);
        when(modelService.getAlleDateiTypenByUser(any())).thenReturn(dateiTypen);
        when(modelService.getAccountFromKeycloak(any())).thenReturn(new Account("BennyGoodman", "nice.de",
                "image", dateiTypen));
        when(modelService.getDateiById(anyLong(), any())).thenReturn(null);
    }

    //Unknown User Access tests

    @Test
    void testStartUnknownUser() throws Exception {
        mvc.perform(get("/material2/"))
                .andExpect(status().isOk());
    }

    @Test
    void testSucheUnknownUser() throws Exception {
        mvc.perform(get("/material2/suche"))
                .andExpect(status().is3xxRedirection());

        mvc.perform(post("/material2/suche").with(csrf()))
                .andExpect(status().is3xxRedirection());
    }

    @Test
    void testUploadUnknownUser() throws Exception {
        mvc.perform(get("/material2/upload"))
                .andExpect(status().is3xxRedirection());

        mvc.perform(post("/material2/upload")
                .with(csrf()))
                .andExpect(status().is3xxRedirection());
    }

    @Test
    void testDateisichtUnknownUser() throws Exception {
        mvc.perform(get("/material2/dateiSicht"))
                .andExpect(status().is3xxRedirection());
    }

    @Test
    void testUpdateUnknownUser() throws Exception {
        mvc.perform(get("/material2/update"))
                .andExpect(status().is3xxRedirection());

        mvc.perform(post("/material2/update")
                .with(csrf()))
                .andExpect(status().is3xxRedirection());
    }

    //Not Allowed Role Access tests

    @Test
    @WithMockKeycloackAuth(name = "BennyGoodman", roles = "TESTER")
    void testStartPublicUser() throws Exception {
        mvc.perform(get("/material2/"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockKeycloackAuth(name = "Benny Goodman", roles = "TESTER")
    void testSuchePublicUser() throws Exception {
        mvc.perform(get("/material2/suche"))
                .andExpect(status().isForbidden());

        mvc.perform(post("/material2/suche").with(csrf()))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockKeycloackAuth(name = "Benny Goodman", roles = "TESTER")
    void testUploadPublicUser() throws Exception {
        mvc.perform(get("/material2/upload"))
                .andExpect(status().isForbidden());

        mvc.perform(post("/material2/upload")
                .with(csrf()))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockKeycloackAuth(name = "Benny Goodman", roles = "TESTER")
    void testDateisichtPublicUser() throws Exception {
        mvc.perform(get("/material2/dateiSicht"))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockKeycloackAuth(name = "Benny Goodman", roles = "TESTER")
    void testUpdatePublicUser() throws Exception {
        mvc.perform(get("/material2/update"))
                .andExpect(status().isForbidden());

        mvc.perform(post("/material2/update")
                .with(csrf()))
                .andExpect(status().isForbidden());
    }

    //Student User Access tests

    @Test
    @WithMockKeycloackAuth(name = "Bruce W.", roles = "studentin")
    void testStartStudentUser() throws Exception {
        mvc.perform(get("/material2/"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockKeycloackAuth(name = "Bruce W.", roles = "studentin")
    void testSucheStudentUser() throws Exception {
        mvc.perform(get("/material2/suche"))
                .andExpect(status().isOk());

        mvc.perform(post("/material2/suche")
                .with(csrf()))
                .andExpect(status().is3xxRedirection());
    }

    @Test
    @WithMockKeycloackAuth(name = "Bruce W.", roles = "studentin")
    void testUploadStudentUser() throws Exception {
        mvc.perform(get("/material2/upload"))
                .andExpect(status().isOk());

        mvc.perform(post("/material2/upload")
                .with(csrf()))
                .andExpect(status().isOk());
    }


    @Test
    @WithMockKeycloackAuth(name = "Bruce W.", roles = "studentin")
    void testDateisichtStudentUser() throws Exception {
        mvc.perform(get("/material2/dateiSicht"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockKeycloackAuth(name = "Bruce W.", roles = "studentin")
    void testUpdateStudentUser() throws Exception {


        mvc.perform(get("/material2/update")
                .param("gruppenId", "1")
                .param("dateiId", "1"))
                .andExpect(status().is3xxRedirection());

        mvc.perform(post("/material2/update")
                .with(csrf()))
                .andExpect(status().is3xxRedirection());
    }

    //Orga User Access tests

    @Test
    @WithMockKeycloackAuth(name = "Donald T.", roles = "orga")
    void testStartOrgaUser() throws Exception {
        mvc.perform(get("/material2/"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockKeycloackAuth(name = "Donald T.", roles = "orga")
    void testSucheOrgaUser() throws Exception {
        mvc.perform(get("/material2/suche"))
                .andExpect(status().isOk());

        mvc.perform(post("/material2/suche")
                .with(csrf()))
                .andExpect(status().is3xxRedirection());
    }

    @Test
    @WithMockKeycloackAuth(name = "Donald T.", roles = "orga")
    void testUploadOrgaUser() throws Exception {
        mvc.perform(get("/material2/upload"))
                .andExpect(status().isOk());

        mvc.perform(post("/material2/upload")
                .with(csrf()))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockKeycloackAuth(name = "Donald T.", roles = "orga")
    void testDateisichtOrgaUser() throws Exception {
        mvc.perform(get("/material2/dateiSicht"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockKeycloackAuth(name = "Donald T.", roles = "orga")
    void testUpdateOrgaUser() throws Exception {
        mvc.perform(get("/material2/update")
                .param("gruppenId", "1")
                .param("dateiId", "1"))
                .andExpect(status().is3xxRedirection());

        mvc.perform(post("/material2/update")
                .with(csrf()))
                .andExpect(status().is3xxRedirection());
    }

    //Actuator User Access tests

    @Test
    @WithMockKeycloackAuth(name = "James B.", roles = "actuator")
    void testStartActuatorUser() throws Exception {
        mvc.perform(get("/material2/"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockKeycloackAuth(name = "James B.", roles = "actuator")
    void testSucheActuatorUserUser() throws Exception {
        mvc.perform(get("/material2/suche"))
                .andExpect(status().isOk());

        mvc.perform(post("/material2/suche")
                .with(csrf()))
                .andExpect(status().is3xxRedirection());
    }

    @Test
    @WithMockKeycloackAuth(name = "James B.", roles = "actuator")
    void testUploadActuatorUser() throws Exception {
        mvc.perform(get("/material2/upload"))
                .andExpect(status().isOk());

        mvc.perform(post("/material2/upload")
                .with(csrf()))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockKeycloackAuth(name = "James B.", roles = "actuator")
    void testDateisichtActuatorUser() throws Exception {
        mvc.perform(get("/material2/dateiSicht"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockKeycloackAuth(name = "James B.", roles = "actuator")
    void testUpdateActuatorUser() throws Exception {
        mvc.perform(get("/material2/update")
                .param("gruppenId", "1")
                .param("dateiId", "1"))
                .andExpect(status().is3xxRedirection());

        mvc.perform(post("/material2/update")
                .with(csrf()))
                .andExpect(status().is3xxRedirection());
    }

}
