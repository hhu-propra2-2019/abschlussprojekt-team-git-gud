package de.hhu.propra2.material2.mops.domain.services;

import de.hhu.propra2.material2.mops.domain.models.Datei;
import de.hhu.propra2.material2.mops.domain.models.Gruppe;
import de.hhu.propra2.material2.mops.domain.models.Tag;
import de.hhu.propra2.material2.mops.domain.models.UpdateForm;
import de.hhu.propra2.material2.mops.domain.models.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.keycloak.adapters.springsecurity.token.KeycloakAuthenticationToken;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.comparesEqualTo;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;
import static org.hamcrest.collection.IsIterableContainingInOrder.contains;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SuppressWarnings("checkstyle:magicnumber")
@ExtendWith(MockitoExtension.class)
class UpdateServiceTest {
    private static final LocalDate DATE_1303 = LocalDate.of(2020, Calendar.MARCH, 13);

    private Tag tag1 = new Tag(1, "tag1");
    private Tag tag2 = new Tag(2, "tag2");
    private Tag tag3 = new Tag(3, "tag3");
    private List<Tag> tags = new ArrayList<>();
    @Mock
    private ModelService modelServiceMock;
    @Mock
    private User userMock;
    @Mock
    private Gruppe gruppenMock;
    @Mock
    private KeycloakAuthenticationToken tokenMock;


    private UpdateService updateService;

    /**
     * setUP: SetUp needed for each test.
     */
    @BeforeEach
    void setUp() throws SQLException {
        updateService = new UpdateService(modelServiceMock);

        Datei datei = new Datei(1L, "test.txt", userMock, tags,
                DATE_1303, DATE_1303, 2L, "txt", "kategorie");
        Mockito.lenient().when(modelServiceMock.getDateiById(1L, tokenMock)).thenReturn(datei);
        when(modelServiceMock.findUserByKeycloakname(anyString())).thenReturn(userMock);
        when(userMock.getGruppeById(1L)).thenReturn(gruppenMock);
        when(gruppenMock.getDateiById(1L)).thenReturn(datei);

        tag1 = new Tag(1, "tag1");
        tag2 = new Tag(2, "tag2");
        tag3 = new Tag(3, "tag3");
        tags = new ArrayList<>();
    }

    @Test
    void updateFileBySettingVeroeffentlichungsdatumAndTagsNull() throws Exception {
        when(userMock.hasUploadPermission(gruppenMock)).thenReturn(true);
        UpdateForm updateForm = new UpdateForm(null, null);
        updateService.startUpdate(updateForm, "", 1L, 1L);

        ArgumentCaptor<Datei> dateiCaptor = ArgumentCaptor.forClass(Datei.class);
        verify(modelServiceMock, times(1)).saveDatei(dateiCaptor.capture(), anyLong());

        Datei capturedDatei = dateiCaptor.getValue();
        assertThat(capturedDatei.getName(), comparesEqualTo("test.txt"));
        assertThat(capturedDatei.getUploader(), equalTo(userMock));
        assertThat(capturedDatei.getTags(), empty());
        assertThat(capturedDatei.getVeroeffentlichungsdatum(), nullValue());
        assertThat(capturedDatei.getDateigroesse(), comparesEqualTo(2L));
        assertThat(capturedDatei.getDateityp(), comparesEqualTo("txt"));
    }

    @Test
    void updateFileBySettingVeroeffentlichungsdatumAndTagsNotNull() throws Exception {
        when(userMock.hasUploadPermission(gruppenMock)).thenReturn(true);
        String stringTags = tag1.getText() + ", " + tag2.getText() + ", " + tag3.getText();
        UpdateForm updateForm = new UpdateForm(stringTags, null);

        updateService.startUpdate(updateForm, "", 1L, 1L);

        ArgumentCaptor<Datei> dateiCaptor = ArgumentCaptor.forClass(Datei.class);
        verify(modelServiceMock, times(1)).saveDatei(dateiCaptor.capture(), anyLong());

        Datei capturedDatei = dateiCaptor.getValue();
        assertThat(capturedDatei.getName(), comparesEqualTo("test.txt"));
        assertThat(capturedDatei.getUploader(), equalTo(userMock));
        assertThat(capturedDatei.getTags(), contains(
                hasProperty("text", is("tag1")),
                hasProperty("text", is("tag2")),
                hasProperty("text", is("tag3"))));
        assertThat(capturedDatei.getVeroeffentlichungsdatum(), nullValue());
        assertThat(capturedDatei.getDateigroesse(), comparesEqualTo(2L));
        assertThat(capturedDatei.getDateityp(), comparesEqualTo("txt"));
    }
}
