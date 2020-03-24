package de.hhu.propra2.material2.mops.controller;

import com.c4_soft.springaddons.test.security.context.support.WithMockKeycloackAuth;
import de.hhu.propra2.material2.mops.Exceptions.NoUploadPermissionException;
import de.hhu.propra2.material2.mops.domain.models.Gruppe;
import de.hhu.propra2.material2.mops.domain.services.MinIOService;
import de.hhu.propra2.material2.mops.domain.services.ModelService;
import de.hhu.propra2.material2.mops.domain.services.UploadService;
import de.hhu.propra2.material2.mops.security.Account;
import org.apache.tomcat.util.http.fileupload.FileUploadException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.keycloak.adapters.springboot.KeycloakSpringBootConfigResolver;
import org.keycloak.adapters.springsecurity.KeycloakSecurityComponents;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.web.servlet.MockMvc;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

@WebMvcTest
@ComponentScan(basePackageClasses = {KeycloakSecurityComponents.class, KeycloakSpringBootConfigResolver.class})
public class MaterialControllerModelTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private ModelService modelService;

    @MockBean
    private MinIOService minIOService;

    @MockBean
    private UploadService uploadService;

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
        when(modelService.getAlleTagsByUser(any())).thenReturn(tags);
        when(modelService.getAlleUploaderByUser(any())).thenReturn(uploader);
        when(modelService.getAlleDateiTypenByUser(any())).thenReturn(dateiTypen);
        when(modelService.getAccountFromKeycloak(any())).thenReturn(new Account("BennyGoodman", ".de",
                "aaa", dateiTypen));
    }

    // Startseite Test

    @Test
    void startEmptyGroupTabsIfUnknownUser() throws Exception {
        mvc.perform(get("/"));
        verify(modelService, never()).getAlleGruppenByUser(any());

    }

    @Test
    @WithMockKeycloackAuth(name = "BennyGoodman", roles = "TESTER")
    void startTestGruppenTabsGetCreated() throws Exception {
        mvc.perform(get("/"))
                .andExpect(content().string(containsString("ProPra")))
                .andExpect(content().string(containsString("RDB")));
        verify(modelService, times(1)).getAlleGruppenByUser(any());
    }

    @Test
    void testReturnStartUnknownUser() throws Exception {
        mvc.perform(get("/"))
                .andExpect(content().string(containsString("Wilkommen bei der Materialsammlung")));
    }

    @Test
    @WithMockKeycloackAuth(name = "BennyGoodman", roles = "TESTER")
    void testReturnStartLogedInUser() throws Exception {
        mvc.perform(get("/"))
                .andExpect(content().string(containsString("Wilkommen bei der Materialsammlung")));
    }

    // Upload Test

    @Test
    @WithMockKeycloackAuth(name = "BennyGoodman", roles = "studentin")
    void uploadTestTabsGetCreated() throws Exception {
        mvc.perform(get("/upload"))
                .andExpect(content().string(containsString("ProPra")))
                .andExpect(content().string(containsString("RDB")));
        verify(modelService, times(1)).getAlleGruppenByUser(any());
    }

    @Test
    @WithMockKeycloackAuth(name = "BennyGoodman", roles = "studentin")
    void uploadTestTagsGetLoaded() throws Exception {
        mvc.perform(get("/upload"));
        verify(modelService, times(1)).getAlleTagsByUser(any());
    }

    @Test
    @WithMockKeycloackAuth(name = "BennyGoodman", roles = "studentin")
    void testReturnUploadTemplate() throws Exception {
        mvc.perform(get("/upload"))
                .andExpect(content().string(containsString("Upload")));
    }

    @Test
    @WithMockKeycloackAuth(name = "BennyGoodman", roles = "studentin")
    void testUploadPostSuccessful() throws Exception {
        mvc.perform(post("/upload")
                .with(csrf()))
                .andExpect(content()
                        .string(containsString("Upload war erfolgreich!")));

        verify(uploadService, times(1)).startUpload(any(), any());
    }

    @Test
    @WithMockKeycloackAuth(name = "BennyGoodman", roles = "studentin")
    void testUploadPostFileUploadException() throws Exception {
        doThrow(new FileUploadException()).when(uploadService).startUpload(any(), any());

        mvc.perform(post("/upload")
                .with(csrf()))
                .andExpect(content()
                        .string(containsString("Beim Upload gab es ein Problem.")));

        verify(uploadService, times(1)).startUpload(any(), any());
    }

    @Test
    @WithMockKeycloackAuth(name = "BennyGoodman", roles = "studentin")
    void testUploadPostSQLException() throws Exception {
        doThrow(new SQLException()).when(uploadService).startUpload(any(), any());

        mvc.perform(post("/upload")
                .with(csrf()))
                .andExpect(content()
                        .string(containsString("Beim speichern in der Datenbank gab es einen Fehler.")));

        verify(uploadService, times(1)).startUpload(any(), any());
    }

    @Test
    @WithMockKeycloackAuth(name = "BennyGoodman", roles = "studentin")
    void testUploadPostNoUploadPermissionException() throws Exception {
        doThrow(new NoUploadPermissionException()).when(uploadService).startUpload(any(), any());

        mvc.perform(post("/upload")
                .with(csrf()))
                .andExpect(content()
                        .string(containsString("Sie sind nicht berechtig in dieser Gruppe hochzuladen!")));

        verify(uploadService, times(1)).startUpload(any(), any());
    }

    //Suche Test

    @Test
    @WithMockKeycloackAuth(name = "studentin1", roles = "studentin")
    void sucheTestGruppenTabsGetCreated() throws Exception {
        mvc.perform(get("/suche"))
                .andExpect(content().string(containsString("ProPra")))
                .andExpect(content().string(containsString("RDB")));
        verify(modelService, times(1)).getAlleGruppenByUser(any());
    }

    @Test
    @WithMockKeycloackAuth(name = "studentin3", roles = "studentin")
    void sucheTestTagsGetLoaded() throws Exception {
        mvc.perform(get("/suche"))
                .andExpect(content().string(containsString("Vorlesung")))
                .andExpect(content().string(containsString("Übung")));
        verify(modelService, times(1)).getAlleTagsByUser(any());
    }

    @Test
    @WithMockKeycloackAuth(name = "studentin2", roles = "studentin")
    void sucheTestDateiTypenGetLoaded() throws Exception {
        mvc.perform(get("/suche"))
                .andExpect(content().string(containsString("JSON")))
                .andExpect(content().string(containsString("XML")));
        verify(modelService, times(1)).getAlleUploaderByUser(any());
    }

    @Test
    @WithMockKeycloackAuth(name = "studentin1", roles = "studentin")
    void sucheTestUploaderGetLoaded() throws Exception {
        mvc.perform(get("/suche"))
                .andExpect(content().string(containsString("Chris")))
                .andExpect(content().string(containsString("Christian")))
                .andExpect(content().string(containsString("Christiano Ronaldo")));
        verify(modelService, times(1)).getAlleUploaderByUser(any());
    }

    @Test
    @WithMockKeycloackAuth(name = "Max Mustermann", roles = "studentin")
    void testReturnSucheTemplate() throws Exception {
        mvc.perform(get("/suche"))
                .andExpect(content().string(containsString("Suche")));
    }
}
