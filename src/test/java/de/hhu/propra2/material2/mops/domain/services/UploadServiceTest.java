package de.hhu.propra2.material2.mops.domain.services;

import de.hhu.propra2.material2.mops.domain.models.Datei;
import de.hhu.propra2.material2.mops.domain.models.Gruppe;
import de.hhu.propra2.material2.mops.domain.models.Tag;
import de.hhu.propra2.material2.mops.domain.models.User;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.comparesEqualTo;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SuppressWarnings("checkstyle:magicnumber")
@ExtendWith(MockitoExtension.class)
public class UploadServiceTest {
    private static Date date1303;
    private Tag tag1 = new Tag(1, "tag1");
    private Tag tag2 = new Tag(2, "tag2");
    private Tag tag3 = new Tag(3, "tag3");
    private List<Tag> tags = new ArrayList<>();
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
     * setDates: sets the dates before all tests
     */
    @BeforeAll
    public static void setDates() {

    }

    /**
     * setUP: SetUp needed for each test.
     */
    @BeforeEach
    public void setUp() {
        Calendar calender = Calendar.getInstance();
        calender.set(2020, Calendar.MARCH, 13);
        date1303 = calender.getTime();

        this.uploadService = new UploadService(modelServiceMock, fileUploadServiceMock);
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
        tags.add(tag1);
        tags.add(tag2);
        tags.add(tag3);
    }

    @Test
    public void uploadFileWithoutNewFileName() throws Exception {
        tags.add(tag1);
        tags.add(tag2);
        tags.add(tag3);

        when(fileUploadServiceMock.upload(file, null, "1"))
                .thenReturn("1/test.txt");
        Datei datei = uploadService.dateiHochladen(file, null, userMock, gruppeMock, date1303, tags);

        assertThat(datei.getName(), comparesEqualTo("test.txt"));
        assertThat(datei.getPfad(), comparesEqualTo("1/test.txt"));
        assertThat(datei.getUploader(), equalTo(userMock));
        assertThat(datei.getTags(), equalTo(tags));
        assertThat(datei.getVeroeffentlichungsdatum(), equalTo(date1303));
        assertThat(datei.getDateigroesse(), comparesEqualTo(72L));
        assertThat(datei.getDateityp(), comparesEqualTo("txt"));
        verify(modelServiceMock, times(1)).saveDatei(datei, gruppeMock);
    }

    @Test
    public void uploadFileWithNewFileNameWithoutExtension() throws Exception {
        tags.add(tag1);
        tags.add(tag2);
        tags.add(tag3);

        when(fileUploadServiceMock.upload(file, "Humbug", "1"))
                .thenReturn("1/Humbug.txt");
        Datei datei = uploadService.dateiHochladen(file, "Humbug", userMock, gruppeMock, date1303, tags);

        assertThat(datei.getName(), comparesEqualTo("Humbug.txt"));
        assertThat(datei.getPfad(), comparesEqualTo("1/Humbug.txt"));
        assertThat(datei.getUploader(), equalTo(userMock));
        assertThat(datei.getTags(), equalTo(tags));
        assertThat(datei.getVeroeffentlichungsdatum(), equalTo(date1303));
        assertThat(datei.getDateigroesse(), comparesEqualTo(72L));
        assertThat(datei.getDateityp(), comparesEqualTo("txt"));
        verify(modelServiceMock, times(1)).saveDatei(datei, gruppeMock);
    }

    @Test
    public void uploadFileWithNewFileNameWithExtension() throws Exception {
        tags.add(tag1);
        tags.add(tag2);
        tags.add(tag3);

        when(fileUploadServiceMock.upload(file, "Humbug.pdf", "1"))
                .thenReturn("1/Humbug.pdf");
        Datei datei = uploadService.dateiHochladen(file, "Humbug.pdf", userMock, gruppeMock, date1303, tags);

        assertThat(datei.getName(), comparesEqualTo("Humbug.pdf"));
        assertThat(datei.getPfad(), comparesEqualTo("1/Humbug.pdf"));
        assertThat(datei.getUploader(), equalTo(userMock));
        assertThat(datei.getTags(), equalTo(tags));
        assertThat(datei.getVeroeffentlichungsdatum(), equalTo(date1303));
        assertThat(datei.getDateigroesse(), comparesEqualTo(72L));
        assertThat(datei.getDateityp(), comparesEqualTo("pdf"));
        verify(modelServiceMock, times(1)).saveDatei(datei, gruppeMock);
    }
}
