package de.hhu.propra2.material2.mops.domain.services;

import de.hhu.propra2.material2.mops.Exceptions.NoUploadPermissionException;
import de.hhu.propra2.material2.mops.domain.models.Datei;
import de.hhu.propra2.material2.mops.domain.models.Gruppe;
import de.hhu.propra2.material2.mops.domain.models.UploadForm;
import de.hhu.propra2.material2.mops.domain.models.User;
import org.apache.tomcat.util.http.fileupload.FileUploadException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.nio.charset.StandardCharsets;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.Month;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.comparesEqualTo;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.collection.IsIterableContainingInOrder.contains;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SuppressWarnings("checkstyle:magicnumber")
@ExtendWith(MockitoExtension.class)
public class UploadServiceTest {
    private static final String TEST_USER_NAME = "testuser";
    @Mock
    private MinIOService minIOServiceMock;
    @Mock
    private ModelService modelServiceMock;
    @Mock
    private User userMock;
    @Mock
    private Gruppe gruppeMock;
    private UploadService uploadService;
    private MultipartFile file;
    private UploadForm uploadForm;

    /**
     * setUP: SetUp needed for each test.
     */
    @BeforeEach
    public void setUp() throws SQLException {
        uploadService = new UploadService(modelServiceMock, minIOServiceMock);

        file = new MockMultipartFile("test.txt",
                "test.txt",
                "text/plain",
                "Night gathers and now my watch begins it shall not end until my death..."
                        .getBytes(StandardCharsets.UTF_8));

        uploadForm = new UploadForm();
        uploadForm.setGruppenId(1L);
        uploadForm.setDatei(file);

        when(modelServiceMock.findUserByKeycloakname(any())).thenReturn(userMock);
        when(userMock.hasUploadPermission(any())).thenReturn(true);
        when(userMock.getGruppeById(1L)).thenReturn(gruppeMock);
    }

    @Test
    public void startUploadWithNoUploadPermission() {
        when(userMock.hasUploadPermission(any())).thenReturn(false);

        Exception exception = assertThrows(NoUploadPermissionException.class, ()
                -> uploadService.startUpload(uploadForm, TEST_USER_NAME));

        assertThat(exception.getMessage(), containsString("no upload permission"));
    }

    @Test
    public void minioUploadFails() {
        when(minIOServiceMock.upload(any(), anyString())).thenReturn(false);

        Exception exception = assertThrows(FileUploadException.class, () ->
                uploadService.startUpload(uploadForm, TEST_USER_NAME));

        assertThat(exception.getMessage(), containsString("Could not save file"));
    }

    @Test
    public void uploadFileSaveDateiIsCalled() throws FileUploadException, SQLException, NoUploadPermissionException {
        when(minIOServiceMock.upload(any(), anyString())).thenReturn(true);

        uploadService.startUpload(uploadForm, TEST_USER_NAME);

        verify(modelServiceMock, times(1)).saveDatei(any(), eq(gruppeMock));
    }

    @Test
    public void uploadFileWithoutNewFileName() throws FileUploadException, SQLException, NoUploadPermissionException {
        uploadForm.setDateiname(null);
        when(minIOServiceMock.upload(any(), anyString())).thenReturn(true);

        uploadService.startUpload(uploadForm, TEST_USER_NAME);

        ArgumentCaptor<Datei> dateiCaptor = ArgumentCaptor.forClass(Datei.class);
        verify(modelServiceMock, times(1)).saveDatei(dateiCaptor.capture(), eq(gruppeMock));
        Datei capturedDatei = dateiCaptor.getValue();
        assertThat(capturedDatei.getName(), comparesEqualTo("test.txt"));
        /*assertThat(capturedDatei.getUploader(), equalTo(userMock));
        assertThat(capturedDatei.getTags(), contains(
                hasProperty("text", is("tag1")),
                hasProperty("text", is("tag2")),
                hasProperty("text", is("tag3"))));
        assertThat(capturedDatei.getVeroeffentlichungsdatum(), nullValue());
        assertThat(capturedDatei.getDateigroesse(), comparesEqualTo(2L));
        assertThat(capturedDatei.getDateityp(), comparesEqualTo("txt"));*/
    }

    @Test
    public void uploadFileWithNewFileNameWithoutExtension() throws FileUploadException, SQLException, NoUploadPermissionException {
        uploadForm.setDateiname("newName");
        when(minIOServiceMock.upload(any(), anyString())).thenReturn(true);

        uploadService.startUpload(uploadForm, TEST_USER_NAME);

        ArgumentCaptor<Datei> dateiCaptor = ArgumentCaptor.forClass(Datei.class);
        verify(modelServiceMock, times(1)).saveDatei(dateiCaptor.capture(), eq(gruppeMock));
        Datei capturedDatei = dateiCaptor.getValue();
        assertThat(capturedDatei.getName(), comparesEqualTo("newName.txt"));
    }

    @Test
    public void uploadFileWithNewFileNameWithExtension() throws FileUploadException, SQLException, NoUploadPermissionException {
        uploadForm.setDateiname("anotherNewName.txt");
        when(minIOServiceMock.upload(any(), anyString())).thenReturn(true);

        uploadService.startUpload(uploadForm, TEST_USER_NAME);

        ArgumentCaptor<Datei> dateiCaptor = ArgumentCaptor.forClass(Datei.class);
        verify(modelServiceMock, times(1)).saveDatei(dateiCaptor.capture(), eq(gruppeMock));
        Datei capturedDatei = dateiCaptor.getValue();
        assertThat(capturedDatei.getName(), comparesEqualTo("anotherNewName.txt"));
    }

    @Test
    public void uploadFileWithoutTags() throws FileUploadException, SQLException, NoUploadPermissionException {
        uploadForm.setSelectedTags(null);
        when(minIOServiceMock.upload(any(), anyString())).thenReturn(true);

        uploadService.startUpload(uploadForm, TEST_USER_NAME);

        ArgumentCaptor<Datei> dateiCaptor = ArgumentCaptor.forClass(Datei.class);
        verify(modelServiceMock, times(1)).saveDatei(dateiCaptor.capture(), eq(gruppeMock));
        Datei capturedDatei = dateiCaptor.getValue();
        assertThat(capturedDatei.getTags(), empty());
    }

    @Test
    public void uploadFileWithEmptyTagString() throws FileUploadException, SQLException, NoUploadPermissionException {
        uploadForm.setSelectedTags("");
        when(minIOServiceMock.upload(any(), anyString())).thenReturn(true);

        uploadService.startUpload(uploadForm, TEST_USER_NAME);

        ArgumentCaptor<Datei> dateiCaptor = ArgumentCaptor.forClass(Datei.class);
        verify(modelServiceMock, times(1)).saveDatei(dateiCaptor.capture(), eq(gruppeMock));
        Datei capturedDatei = dateiCaptor.getValue();
        assertThat(capturedDatei.getTags(), empty());
    }

    @Test
    public void uploadFileWithSpaceTagString() throws FileUploadException, SQLException, NoUploadPermissionException {
        uploadForm.setSelectedTags(" ");
        when(minIOServiceMock.upload(any(), anyString())).thenReturn(true);

        uploadService.startUpload(uploadForm, TEST_USER_NAME);

        ArgumentCaptor<Datei> dateiCaptor = ArgumentCaptor.forClass(Datei.class);
        verify(modelServiceMock, times(1)).saveDatei(dateiCaptor.capture(), eq(gruppeMock));
        Datei capturedDatei = dateiCaptor.getValue();
        assertThat(capturedDatei.getTags(), empty());
    }

    @Test
    public void uploadFileWithTagStringWithCommaAtTheEnd() throws FileUploadException, SQLException, NoUploadPermissionException {
        uploadForm.setSelectedTags("tag1  , tag2,");
        when(minIOServiceMock.upload(any(), anyString())).thenReturn(true);

        uploadService.startUpload(uploadForm, TEST_USER_NAME);

        ArgumentCaptor<Datei> dateiCaptor = ArgumentCaptor.forClass(Datei.class);
        verify(modelServiceMock, times(1)).saveDatei(dateiCaptor.capture(), eq(gruppeMock));
        Datei capturedDatei = dateiCaptor.getValue();
        assertThat(capturedDatei.getTags().size(), equalTo(2));
        assertThat(capturedDatei.getTags(), contains(
                hasProperty("text", is("tag1")),
                hasProperty("text", is("tag2"))));
    }

    @Test
    public void uploadFileAndCheckUploader() throws FileUploadException, SQLException, NoUploadPermissionException {
        when(minIOServiceMock.upload(any(), anyString())).thenReturn(true);

        uploadService.startUpload(uploadForm, TEST_USER_NAME);

        ArgumentCaptor<Datei> dateiCaptor = ArgumentCaptor.forClass(Datei.class);
        verify(modelServiceMock, times(1)).saveDatei(dateiCaptor.capture(), eq(gruppeMock));
        Datei capturedDatei = dateiCaptor.getValue();
        assertThat(capturedDatei.getUploader(), equalTo(userMock));
    }

    @Test
    public void uploadFileAndCheckDateigroesse() throws FileUploadException, SQLException, NoUploadPermissionException {
        when(minIOServiceMock.upload(any(), anyString())).thenReturn(true);

        uploadService.startUpload(uploadForm, TEST_USER_NAME);

        ArgumentCaptor<Datei> dateiCaptor = ArgumentCaptor.forClass(Datei.class);
        verify(modelServiceMock, times(1)).saveDatei(dateiCaptor.capture(), eq(gruppeMock));
        Datei capturedDatei = dateiCaptor.getValue();
        assertThat(capturedDatei.getDateigroesse(), comparesEqualTo(72L));
    }

    @Test
    public void uploadFileAndCheckDateityp() throws FileUploadException, SQLException, NoUploadPermissionException {
        uploadForm.setSelectedTags("tag1  , ");
        when(minIOServiceMock.upload(any(), anyString())).thenReturn(true);

        uploadService.startUpload(uploadForm, TEST_USER_NAME);

        ArgumentCaptor<Datei> dateiCaptor = ArgumentCaptor.forClass(Datei.class);
        verify(modelServiceMock, times(1)).saveDatei(dateiCaptor.capture(), eq(gruppeMock));
        Datei capturedDatei = dateiCaptor.getValue();
        assertThat(capturedDatei.getDateityp(), comparesEqualTo("txt"));
    }

    @Test
    public void uploadFileWithKategorie() throws FileUploadException, SQLException, NoUploadPermissionException {
        uploadForm.setKategorie("Vorlesung");
        when(minIOServiceMock.upload(any(), anyString())).thenReturn(true);

        uploadService.startUpload(uploadForm, TEST_USER_NAME);

        ArgumentCaptor<Datei> dateiCaptor = ArgumentCaptor.forClass(Datei.class);
        verify(modelServiceMock, times(1)).saveDatei(dateiCaptor.capture(), eq(gruppeMock));
        Datei capturedDatei = dateiCaptor.getValue();
        assertThat(capturedDatei.getKategorie(), comparesEqualTo("Vorlesung"));
    }

    @Test
    public void uploadFileWithTimedUpload() throws FileUploadException, SQLException, NoUploadPermissionException {
        uploadForm.setTimedUpload("2020-03-13");
        when(minIOServiceMock.upload(any(), anyString())).thenReturn(true);

        uploadService.startUpload(uploadForm, TEST_USER_NAME);

        ArgumentCaptor<Datei> dateiCaptor = ArgumentCaptor.forClass(Datei.class);
        verify(modelServiceMock, times(1)).saveDatei(dateiCaptor.capture(), eq(gruppeMock));
        Datei capturedDatei = dateiCaptor.getValue();
        assertThat(capturedDatei.getVeroeffentlichungsdatum(), is(LocalDate.of(2020, Month.MARCH.getValue(), 13)));
    }
}
