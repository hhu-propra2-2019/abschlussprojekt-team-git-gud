package de.hhu.propra2.material2.mops.web;

import com.c4_soft.springaddons.test.security.context.support.WithMockKeycloackAuth;
import de.hhu.propra2.material2.mops.domain.services.MinioDownloadService;
import de.hhu.propra2.material2.mops.domain.services.ModelService;
import org.junit.jupiter.api.Test;
import org.keycloak.adapters.springboot.KeycloakSpringBootConfigResolver;
import org.keycloak.adapters.springsecurity.KeycloakSecurityComponents;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.web.servlet.MockMvc;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
@ComponentScan(basePackageClasses = { KeycloakSecurityComponents.class, KeycloakSpringBootConfigResolver.class })
public class MaterialControllerAccessTest {

    @Autowired
    MockMvc mvc;

    @MockBean
    ModelService modelService;

    @MockBean
    MinioDownloadService minioDownloadService;

    //Unknown User Access tests

    @Test
    void testStart_UnknownUser() throws Exception {
        mvc.perform(get("/"))
                .andExpect(status().isOk());
    }

    @Test
    void testSuche_UnknownUser() throws Exception {
        mvc.perform(get("/suche"))
                .andExpect(status().is3xxRedirection());

        mvc.perform(post("/suche").with(csrf()))
                .andExpect(status().is3xxRedirection());
    }

    @Test
    void testUpload_UnknownUser() throws Exception {
        mvc.perform(get("/upload"))
                .andExpect(status().is3xxRedirection());

        mvc.perform(post("/upload").with(csrf()))
                .andExpect(status().is3xxRedirection());
    }

    @Test
    void testDateisicht_UnknownUser() throws Exception {
        mvc.perform(get("/dateiSicht"))
                .andExpect(status().is3xxRedirection());
    }

    @Test
    void testFileDownload_UnknownUser() throws Exception {
        mvc.perform(get("/files"))
                .andExpect(status().is3xxRedirection());
    }

    //Not Allowed Role Access tests

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

        mvc.perform(post("/suche").with(csrf()))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockKeycloackAuth(name = "Benny Goodman", roles = "TESTER")
    void testUpload_PublicUser() throws Exception {
        mvc.perform(get("/upload"))
                .andExpect(status().isForbidden());

        mvc.perform(post("/upload").with(csrf()))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockKeycloackAuth(name = "Benny Goodman", roles = "TESTER")
    void testDateisicht_PublicUser() throws Exception {
        mvc.perform(get("/dateiSicht"))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockKeycloackAuth(name = "Benny Goodman", roles = "TESTER")
    void testFileDownload_PublicUser() throws Exception {
        mvc.perform(get("/files"))
                .andExpect(status().isForbidden());
    }

    //Student User Access tests

    @Test
    @WithMockKeycloackAuth(name = "Bruce W.", roles = "studentin")
    void testStart_StudentUser() throws Exception {
        mvc.perform(get("/"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockKeycloackAuth(name = "Bruce W.", roles = "studentin")
    void testSuche_StudentUser() throws Exception {
        mvc.perform(get("/suche"))
                .andExpect(status().isOk());

        mvc.perform(post("/suche")
                .with(csrf()))
                .andExpect(status().is3xxRedirection());
    }

    @Test
    @WithMockKeycloackAuth(name = "Bruce W.", roles = "studentin")
    void testUpload_StudentUser() throws Exception {
        mvc.perform(get("/upload"))
                .andExpect(status().isOk());

        mvc.perform(post("/upload")
                .with(csrf()))
                .andExpect(status().is3xxRedirection());
    }


    @Test
    @WithMockKeycloackAuth(name = "Bruce W.", roles = "studentin")
    void testDateisicht_StudentUser() throws Exception {
        mvc.perform(get("/dateiSicht"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockKeycloackAuth(name = "Bruce W.", roles = "studentin")
    void testFileDownload_StudentUser() throws Exception {
        String myString = "hello";
        InputStream inputStream = new ByteArrayInputStream( myString.getBytes() );
        when(minioDownloadService.getObject(any())).thenReturn(inputStream);

        mvc.perform(get("/files"))
                .andExpect(status().isOk());
    }

    //Orga User Access tests

    @Test
    @WithMockKeycloackAuth(name = "Donald T.", roles = "orga")
    void testStart_OrgaUser() throws Exception {
        mvc.perform(get("/"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockKeycloackAuth(name = "Donald T.", roles = "orga")
    void testSuche_OrgaUser() throws Exception {
        mvc.perform(get("/suche"))
                .andExpect(status().isOk());

        mvc.perform(post("/suche")
                .with(csrf()))
                .andExpect(status().is3xxRedirection());
    }

    @Test
    @WithMockKeycloackAuth(name = "Donald T.", roles = "orga")
    void testUpload_OrgaUser() throws Exception {
        mvc.perform(get("/upload"))
                .andExpect(status().isOk());

        mvc.perform(post("/upload")
                .with(csrf()))
                .andExpect(status().is3xxRedirection());
    }

    @Test
    @WithMockKeycloackAuth(name = "Donald T.", roles = "orga")
    void testDateisicht_OrgaUser() throws Exception {
        mvc.perform(get("/dateiSicht"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockKeycloackAuth(name = "Donald T.", roles = "orga")
    void testFileDownload_OrgaUser() throws Exception {
        String myString = "hello";
        InputStream inputStream = new ByteArrayInputStream( myString.getBytes() );
        when(minioDownloadService.getObject(any())).thenReturn(inputStream);

        mvc.perform(get("/files"))
                .andExpect(status().isOk());
    }

    //Actuator User Access tests

    @Test
    @WithMockKeycloackAuth(name = "James B.", roles = "actuator")
    void testStart_ActuatorUser() throws Exception {
        mvc.perform(get("/"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockKeycloackAuth(name = "James B.", roles = "actuator")
    void testSuche_ActuatorUserUser() throws Exception {
        mvc.perform(get("/suche"))
                .andExpect(status().isOk());

        mvc.perform(post("/suche")
                .with(csrf()))
                .andExpect(status().is3xxRedirection());
    }

    @Test
    @WithMockKeycloackAuth(name = "James B.", roles = "actuator")
    void testUpload_ActuatorUser() throws Exception {
        mvc.perform(get("/upload"))
                .andExpect(status().isOk());

        mvc.perform(post("/upload")
                .with(csrf()))
                .andExpect(status().is3xxRedirection());
    }

    @Test
    @WithMockKeycloackAuth(name = "James B.", roles = "actuator")
    void testDateisicht_ActuatorUser() throws Exception {
        mvc.perform(get("/dateiSicht"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockKeycloackAuth(name = "James B.", roles = "actuator")
    void testFileDownload_ActuatorUser() throws Exception {
        String myString = "hello";
        InputStream inputStream = new ByteArrayInputStream( myString.getBytes() );
        when(minioDownloadService.getObject(any())).thenReturn(inputStream);

        mvc.perform(get("/files"))
                .andExpect(status().isOk());
    }
}
