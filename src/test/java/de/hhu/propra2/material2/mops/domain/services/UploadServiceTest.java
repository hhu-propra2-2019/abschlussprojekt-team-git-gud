package de.hhu.propra2.material2.mops.domain.services;

import de.hhu.propra2.material2.mops.Database.Repository;
import de.hhu.propra2.material2.mops.domain.models.Datei;
import de.hhu.propra2.material2.mops.domain.models.Gruppe;
import de.hhu.propra2.material2.mops.domain.models.Tag;
import de.hhu.propra2.material2.mops.domain.models.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.comparesEqualTo;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.collection.IsIterableContainingInOrder.contains;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SuppressWarnings("checkstyle:magicnumber")
@ExtendWith(MockitoExtension.class)
public class UploadServiceTest {
    private static LocalDate date1303;

    private Tag tag1 = new Tag(1, "tag1");
    private Tag tag2 = new Tag(2, "tag2");
    private Tag tag3 = new Tag(3, "tag3");
    private List<Tag> tags = new ArrayList<>();
    @Mock
    private Repository repositoryMock;
    @Mock
    private FileUploadService fileUploadServiceMock;
    @Mock
    private ModelService modelServiceMock;
    @Mock
    private User userMock;
    @Mock
    private Gruppe gruppeMock;
    private UploadService uploadService;
    private MultipartFile file;

    /**
     * setUP: SetUp needed for each test.
     */
    @BeforeEach
    public void setUp() {
        Calendar calender = Calendar.getInstance();
        calender.set(2020, Calendar.MARCH, 13);
        date1303 = LocalDate.of(2020, Calendar.MARCH, 13);

        uploadService = new UploadService(repositoryMock, modelServiceMock, fileUploadServiceMock);
        when(gruppeMock.getId()).thenReturn(1L);

        file = new MockMultipartFile("test.txt",
                "test.txt",
                "text/plain",
                "Night gathers and now my watch begins it shall not end until my death..."
                        .getBytes(StandardCharsets.UTF_8));

        tag1 = new Tag(1, "tag1");
        tag2 = new Tag(2, "tag2");
        tag3 = new Tag(3, "tag3");
        tags = new ArrayList<>();
    }

    @Test
    public void uploadFileProveFileUploadServiceUploadCall() throws Exception {
        when(fileUploadServiceMock.upload(file, "1"))
                .thenReturn(true);

        uploadService.dateiHochladen(file, null, userMock, gruppeMock, date1303, tags);

        verify(fileUploadServiceMock, times(1)).upload(file, "1");
    }

    @Test
    public void uploadFileWithoutNewFileName() throws Exception {
        when(fileUploadServiceMock.upload(file, "1"))
                .thenReturn(true);
        Datei datei = uploadService.dateiHochladen(file, null, userMock, gruppeMock, date1303, tags);

        assertThat(datei.getName(), comparesEqualTo("test.txt"));
        assertThat(datei.getUploader(), equalTo(userMock));
        assertThat(datei.getTags(), equalTo(tags));
        assertThat(datei.getVeroeffentlichungsdatum(), equalTo(date1303));
        assertThat(datei.getDateigroesse(), comparesEqualTo(72L));
        assertThat(datei.getDateityp(), comparesEqualTo("txt"));
        verify(modelServiceMock, times(1)).saveDatei(datei, gruppeMock);
    }

    @Test
    public void uploadFileWithNewFileNameWithoutExtension() throws Exception {
        when(fileUploadServiceMock.upload(file, "1"))
                .thenReturn(true);
        Datei datei = uploadService.dateiHochladen(file, "Humbug", userMock, gruppeMock, date1303, tags);

        assertThat(datei.getName(), comparesEqualTo("Humbug.txt"));
        assertThat(datei.getUploader(), equalTo(userMock));
        assertThat(datei.getTags(), equalTo(tags));
        assertThat(datei.getVeroeffentlichungsdatum(), equalTo(date1303));
        assertThat(datei.getDateigroesse(), comparesEqualTo(72L));
        assertThat(datei.getDateityp(), comparesEqualTo("txt"));
        verify(modelServiceMock, times(1)).saveDatei(datei, gruppeMock);
    }

    @Test
    public void uploadFileWithNewFileNameWithExtension() throws Exception {
        when(fileUploadServiceMock.upload(file, "1"))
                .thenReturn(true);
        Datei datei = uploadService.dateiHochladen(file, "Humbug.pdf", userMock, gruppeMock, date1303, tags);

        assertThat(datei.getName(), comparesEqualTo("Humbug.pdf"));
        assertThat(datei.getUploader(), equalTo(userMock));
        assertThat(datei.getTags(), equalTo(tags));
        assertThat(datei.getVeroeffentlichungsdatum(), equalTo(date1303));
        assertThat(datei.getDateigroesse(), comparesEqualTo(72L));
        assertThat(datei.getDateityp(), comparesEqualTo("pdf"));
        verify(modelServiceMock, times(1)).saveDatei(datei, gruppeMock);
    }

    @Test
    public void uploadFileWithTags() throws Exception {
        when(fileUploadServiceMock.upload(file, "1"))
                .thenReturn(true);
        tags.add(tag1);
        tags.add(tag2);
        tags.add(tag3);

        Datei datei = uploadService.dateiHochladen(file, null, userMock, gruppeMock, date1303, tags);

        assertThat(datei.getTags().size(), equalTo(3));
        assertThat(datei.getTags(), contains(
                hasProperty("text", is("tag1")),
                hasProperty("text", is("tag2")),
                hasProperty("text", is("tag3"))
        ));
    }
}
