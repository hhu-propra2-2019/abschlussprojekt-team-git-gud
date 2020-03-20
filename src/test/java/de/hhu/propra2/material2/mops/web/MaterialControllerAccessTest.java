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
    private MockMvc mvc;

    @MockBean
    private ModelService modelService;

    @MockBean
    private MinioDownloadService minioDownloadService;

    //Unknown User Access tests

    @Test
    void testStartUnknownUser() throws Exception {
        mvc.perform(get("/"))
                .andExpect(status().isOk());
    }

    @Test
    void testSucheUnknownUser() throws Exception {
        mvc.perform(get("/suche"))
                .andExpect(status().is3xxRedirection());

        mvc.perform(post("/suche").with(csrf()))
                .andExpect(status().is3xxRedirection());
    }

    @Test
    void testUploadUnknownUser() throws Exception {
        mvc.perform(get("/upload"))
                .andExpect(status().is3xxRedirection());

        mvc.perform(post("/upload").with(csrf()))
                .andExpect(status().is3xxRedirection());
    }

    @Test
    void testDateisichtUnknownUser() throws Exception {
        mvc.perform(get("/dateiSicht"))
                .andExpect(status().is3xxRedirection());
    }

    @Test
    void testFileDownloadUnknownUser() throws Exception {
        mvc.perform(get("/files"))
                .andExpect(status().is3xxRedirection());
    }

    //Not Allowed Role Access tests

    @Test
    @WithMockKeycloackAuth(name = "BennyGoodman", roles = "TESTER")
    void testStartPublicUser() throws Exception {
        mvc.perform(get("/"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockKeycloackAuth(name = "Benny Goodman", roles = "TESTER")
    void testSuchePublicUser() throws Exception {
        mvc.perform(get("/suche"))
                .andExpect(status().isForbidden());

        mvc.perform(post("/suche").with(csrf()))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockKeycloackAuth(name = "Benny Goodman", roles = "TESTER")
    void testUploadPublicUser() throws Exception {
        mvc.perform(get("/upload"))
                .andExpect(status().isForbidden());

        mvc.perform(post("/upload").with(csrf()))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockKeycloackAuth(name = "Benny Goodman", roles = "TESTER")
    void testDateisichtPublicUser() throws Exception {
        mvc.perform(get("/dateiSicht"))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockKeycloackAuth(name = "Benny Goodman", roles = "TESTER")
    void testFileDownloadPublicUser() throws Exception {
        mvc.perform(get("/files"))
                .andExpect(status().isForbidden());
    }

    //Student User Access tests

    @Test
    @WithMockKeycloackAuth(name = "Bruce W.", roles = "studentin")
    void testStartStudentUser() throws Exception {
        mvc.perform(get("/"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockKeycloackAuth(name = "Bruce W.", roles = "studentin")
    void testSucheStudentUser() throws Exception {
        mvc.perform(get("/suche"))
                .andExpect(status().isOk());

        mvc.perform(post("/suche")
                .with(csrf()))
                .andExpect(status().is3xxRedirection());
    }

    @Test
    @WithMockKeycloackAuth(name = "Bruce W.", roles = "studentin")
    void testUploadStudentUser() throws Exception {
        mvc.perform(get("/upload"))
                .andExpect(status().isOk());

        mvc.perform(post("/upload")
                .with(csrf()))
                .andExpect(status().is3xxRedirection());
    }


    @Test
    @WithMockKeycloackAuth(name = "Bruce W.", roles = "studentin")
    void testDateisichtStudentUser() throws Exception {
        mvc.perform(get("/dateiSicht"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockKeycloackAuth(name = "Bruce W.", roles = "studentin")
    void testFileDownloadStudentUser() throws Exception {
        String myString = "hello";
        InputStream inputStream = new ByteArrayInputStream( myString.getBytes() );
        when(minioDownloadService.getObject(any())).thenReturn(inputStream);

        mvc.perform(get("/files"))
                .andExpect(status().isOk());
    }

    //Orga User Access tests

    @Test
    @WithMockKeycloackAuth(name = "Donald T.", roles = "orga")
    void testStartOrgaUser() throws Exception {
        mvc.perform(get("/"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockKeycloackAuth(name = "Donald T.", roles = "orga")
    void testSucheOrgaUser() throws Exception {
        mvc.perform(get("/suche"))
                .andExpect(status().isOk());

        mvc.perform(post("/suche")
                .with(csrf()))
                .andExpect(status().is3xxRedirection());
    }

    @Test
    @WithMockKeycloackAuth(name = "Donald T.", roles = "orga")
    void testUploadOrgaUser() throws Exception {
        mvc.perform(get("/upload"))
                .andExpect(status().isOk());

        mvc.perform(post("/upload")
                .with(csrf()))
                .andExpect(status().is3xxRedirection());
    }

    @Test
    @WithMockKeycloackAuth(name = "Donald T.", roles = "orga")
    void testDateisichtOrgaUser() throws Exception {
        mvc.perform(get("/dateiSicht"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockKeycloackAuth(name = "Donald T.", roles = "orga")
    void testFileDownloadOrgaUser() throws Exception {
        String myString = "hello";
        InputStream inputStream = new ByteArrayInputStream( myString.getBytes() );
        when(minioDownloadService.getObject(any())).thenReturn(inputStream);

        mvc.perform(get("/files"))
                .andExpect(status().isOk());
    }

    //Actuator User Access tests

    @Test
    @WithMockKeycloackAuth(name = "James B.", roles = "actuator")
    void testStartActuatorUser() throws Exception {
        mvc.perform(get("/"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockKeycloackAuth(name = "James B.", roles = "actuator")
    void testSucheActuatorUserUser() throws Exception {
        mvc.perform(get("/suche"))
                .andExpect(status().isOk());

        mvc.perform(post("/suche")
                .with(csrf()))
                .andExpect(status().is3xxRedirection());
    }

    @Test
    @WithMockKeycloackAuth(name = "James B.", roles = "actuator")
    void testUploadActuatorUser() throws Exception {
        mvc.perform(get("/upload"))
                .andExpect(status().isOk());

        mvc.perform(post("/upload")
                .with(csrf()))
                .andExpect(status().is3xxRedirection());
    }

    @Test
    @WithMockKeycloackAuth(name = "James B.", roles = "actuator")
    void testDateisichtActuatorUser() throws Exception {
        mvc.perform(get("/dateiSicht"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockKeycloackAuth(name = "James B.", roles = "actuator")
    void testFileDownloadActuatorUser() throws Exception {
        String myString = "hello";
        InputStream inputStream = new ByteArrayInputStream( myString.getBytes() );
        when(minioDownloadService.getObject(any())).thenReturn(inputStream);

        mvc.perform(get("/files"))
                .andExpect(status().isOk());
    }
}
