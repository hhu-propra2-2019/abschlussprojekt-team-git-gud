package de.hhu.propra2.material2.mops.domain.services;

import de.hhu.propra2.material2.mops.domain.models.Datei;
import de.hhu.propra2.material2.mops.domain.models.Tag;
import de.hhu.propra2.material2.mops.domain.models.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
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
import static org.hamcrest.Matchers.nullValue;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SuppressWarnings("checkstyle:magicnumber")
@ExtendWith(MockitoExtension.class)
public class UpdateServiceTest {
    private static LocalDate date1303 = LocalDate.of(2020, Calendar.MARCH, 13);

    private Tag tag1 = new Tag(1, "tag1");
    private Tag tag2 = new Tag(2, "tag2");
    private Tag tag3 = new Tag(3, "tag3");
    private List<Tag> tags = new ArrayList<>();
    @Mock
    private ModelService modelServiceMock;
    @Mock
    private User userMock;

    private UpdateService updateService;

    /**
     * setUP: SetUp needed for each test.
     */
    @BeforeEach
    public void setUp() throws SQLException {
        updateService = new UpdateService(modelServiceMock);

        Datei datei = new Datei(1L, "test.txt", userMock, tags,
                date1303, date1303, 2L, "txt", "kategorie");
        when(modelServiceMock.findDateiById(1L)).thenReturn(datei);

        tag1 = new Tag(1, "tag1");
        tag2 = new Tag(2, "tag2");
        tag3 = new Tag(3, "tag3");
        tags = new ArrayList<>();
    }

    @Test
    public void updateFileBySettingVeroeffentlichungsdatumAndTagsNull() throws Exception {
        updateService.dateiUpdate(1L, 1L, null, null);

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
}
