package de.hhu.propra2.material2.mops.web;

import com.c4_soft.springaddons.test.security.context.support.WithMockKeycloackAuth;
import de.hhu.propra2.material2.mops.domain.models.Gruppe;
import de.hhu.propra2.material2.mops.domain.services.IModelService;
import de.hhu.propra2.material2.mops.domain.services.ModelService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.keycloak.Token;
import org.keycloak.adapters.springboot.KeycloakSpringBootConfigResolver;
import org.keycloak.adapters.springsecurity.KeycloakSecurityComponents;
import org.reactivestreams.Publisher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.web.servlet.MockMvc;

import javax.validation.constraints.AssertFalse;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.contains;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
@ComponentScan(basePackageClasses = { KeycloakSecurityComponents.class, KeycloakSpringBootConfigResolver.class })
public class MaterialControllerModelTest {

    @Autowired
    MockMvc mvc;

    @MockBean
    ModelService modelService;

    @BeforeEach
    void init()
    {
        List<Gruppe> gruppen = new ArrayList<>();
        gruppen.add(new Gruppe(1,"ProPra", null));
        gruppen.add(new Gruppe(2,"RDB", null));
        Set<String> tags = new HashSet<>();
        tags.add("Vorlesung");
        tags.add("Übung");
        when(modelService.getAlleGruppenByUser(any())).thenReturn(gruppen);
        when(modelService.getAlleTagsByUser(any())).thenReturn(tags);
    }

    // Startseite Test

    @Test
    void StartEmptyGroupTabsIfUnknownUser() throws Exception {
        mvc.perform(get("/"));
        verify(modelService, never()).getAlleGruppenByUser(any());

    }

    @Test
    @WithMockKeycloackAuth(name = "BennyGoodman", roles = "TESTER")
    void StartTestGruppenTabsGetCreated() throws Exception {
        mvc.perform(get("/"))
                .andExpect(content().string(containsString("ProPra")))
                .andExpect(content().string(containsString("RDB")));
        verify(modelService, times(1)).getAlleGruppenByUser(any());
    }

    @Test
    void TestReturnStartUnknownUser() throws Exception {
        mvc.perform(get("/"))
                .andExpect(content().string(containsString("Wilkommen bei der Materialsammlung")));
    }

    @Test
    @WithMockKeycloackAuth(name = "BennyGoodman", roles = "TESTER")
    void TestReturnStartLogedInUser() throws Exception {
        mvc.perform(get("/"))
                .andExpect(content().string(containsString("Wilkommen bei der Materialsammlung")));
    }

    // Upload Test

    @Test
    @WithMockKeycloackAuth(name = "BennyGoodman", roles = "studentin")
    void UploadTestTabsGetCreated() throws Exception {
        mvc.perform(get("/upload"))
                .andExpect(content().string(containsString("ProPra")))
                .andExpect(content().string(containsString("RDB")));
        verify(modelService, times(1)).getAlleGruppenByUser(any());
    }

    @Test
    @WithMockKeycloackAuth(name = "BennyGoodman", roles = "studentin")
    void UploadTestTagsGetLoaded() throws Exception {
        mvc.perform(get("/upload"));
        verify(modelService, times(1)).getAlleTagsByUser(any());
    }

    @Test
    @WithMockKeycloackAuth(name = "BennyGoodman", roles = "studentin")
    void TestReturnUploadTemplate() throws Exception {
        mvc.perform(get("/upload"))
                .andExpect(content().string(containsString("Upload")));
    }
}
