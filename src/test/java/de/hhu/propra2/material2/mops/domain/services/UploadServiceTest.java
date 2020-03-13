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
    @Mock
    private FileUploadService fileUploadServiceMock;
    @Mock
    private ModelService modelServiceMock;
    @Mock
    private User userMock;
    @Mock
    private Gruppe gruppeMock;
    private UploadService uploadService;

    /**
     * setDates: sets the dates before all tests
     */
    @BeforeAll
    public static void setDates() {
        Calendar calender = Calendar.getInstance();
        calender.set(2020, Calendar.MARCH, 13);
        date1303 = calender.getTime();
    }

    /**
     * setUP: SetUp needed for each test.
     */
    @BeforeEach
    public void setUp() {
        this.uploadService = new UploadService(modelServiceMock, fileUploadServiceMock);
        when(gruppeMock.getId()).thenReturn(1L);
    }

    @Test
    public void dateiWirdHochgeladen() throws Exception {
        MultipartFile file = new MockMultipartFile("test.txt",
                "test.txt",
                "text/plain",
                "Night gathers and now my watch begins it shall not end until my death..."
                        .getBytes(StandardCharsets.UTF_8));

        final Tag tag1 = new Tag(1, "tag1");
        final Tag tag2 = new Tag(2, "tag2");
        final Tag tag3 = new Tag(3, "tag3");

        final List<Tag> tags = new ArrayList<>();
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
}
