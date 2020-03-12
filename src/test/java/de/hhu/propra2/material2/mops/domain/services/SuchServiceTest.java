package de.hhu.propra2.material2.mops.domain.services;

import de.hhu.propra2.material2.mops.Database.DTOs.UserDTO;
import de.hhu.propra2.material2.mops.Database.DateiRepository;
import de.hhu.propra2.material2.mops.Database.GruppeRepository;
import de.hhu.propra2.material2.mops.Database.UserRepository;
import de.hhu.propra2.material2.mops.domain.models.Datei;
import de.hhu.propra2.material2.mops.domain.models.Gruppe;
import de.hhu.propra2.material2.mops.domain.models.Suche;
import de.hhu.propra2.material2.mops.domain.models.Tag;
import de.hhu.propra2.material2.mops.domain.models.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class SuchServiceTest {

    @Mock
    private DateiRepository dateiRepoMock;
    @Mock
    private GruppeRepository gruppenRepoMock;
    @Mock
    private UserRepository userRepoMock;
    @Mock
    private ModelService modelServiceMock;
    @Mock
    private User userMock;
    @Mock
    private Gruppe gruppeMock1;
    @Mock
    private Gruppe gruppeMock2;
    @Mock
    private User uploaderMock;

    private SuchService suchService;
    private List<Datei> dateienGruppe1;
    private List<Datei> dateienGruppe2;
    private Datei datei1;
    private Datei datei2;
    private Datei datei3;
    private Datei datei4;

    /**
     * setUP: SetUp needed for each test.
     */
    @BeforeEach
    @SuppressWarnings("checkstyle:magicnumber")
    public void setUp() {
        this.suchService = new SuchService(dateiRepoMock,
                gruppenRepoMock,
                userRepoMock,
                modelServiceMock);

        Mockito.lenient().when(modelServiceMock.loadUser(any(UserDTO.class))).thenReturn(userMock);
        Mockito.lenient().when(modelServiceMock.loadUser(null)).thenReturn(userMock);

        //Date for Datei
        Calendar calender = Calendar.getInstance();
        calender.set(2020, 1, 3);
        Date date1 = calender.getTime();
        calender.set(2020, 3, 5);
        Date date2 = calender.getTime();
        calender.set(2020, 1, 1);
        Date veroeffentlichung = calender.getTime();

        //Tags for Datei
        Tag tag1 = new Tag(1, "Vorlesung");
        Tag tag2 = new Tag(2, "Relevant");
        Tag tag3 = new Tag(3, "Übung");
        Tag tag4 = new Tag(4, "Irgendwas");
        List<Tag> tags1 = new ArrayList<>(Arrays.asList(tag1, tag2, tag3, tag4));
        List<Tag> tags2 = new ArrayList<>(Arrays.asList(tag1));
        List<Tag> tags3 = new ArrayList<>(Arrays.asList(tag2, tag4));

        //Dateien for List<Datei>
        datei1 = new Datei(1, "1", "a/b/2", uploaderMock, tags1,
                date1, veroeffentlichung, 1, "pdf");
        datei2 = new Datei(2, "2", "a/b/2", uploaderMock, tags2,
                date1, veroeffentlichung, 1, "pdf");
        datei3 = new Datei(3, "3", "a/b/3", uploaderMock, tags3,
                date1, veroeffentlichung, 1, "jpg");
        datei4 = new Datei(4, "4", "a/b/4", uploaderMock, tags3,
                date2, veroeffentlichung, 1, "jpg");
        dateienGruppe1 = new ArrayList<>(Arrays.asList(datei1, datei2, datei3));
        dateienGruppe2 = new ArrayList<>(Arrays.asList(datei4));

        when(gruppeMock1.getDateien()).thenReturn(dateienGruppe1);
        when(gruppeMock2.getDateien()).thenReturn(dateienGruppe2);
        when(userMock.getAllGruppen()).thenReturn(Arrays.asList(gruppeMock1, gruppeMock2));
    }

    @Test
    public void keineDateienInGruppen() {
        when(gruppeMock1.getDateien()).thenReturn(new ArrayList<>());
        when(gruppeMock2.getDateien()).thenReturn(new ArrayList<>());
        Suche suche = new Suche(
                "01.01.2000",
                "31.12.2100",
                null,
                null,
                null,
                null,
                null);

        List<Datei> result = suchService.starteSuche(suche, "Peter");

        final int expectedSizeOfList = 0;
        assertThat(result.size(), is(expectedSizeOfList));
    }

    @Test
    public void keineFilter() {
        Suche suche = new Suche(
                "01.01.2000",
                "31.12.2100",
                null,
                null,
                null,
                null,
                null);

        List<Datei> result = suchService.starteSuche(suche, "Peter");

        final int expectedSizeOfList = 4;
        assertThat(result.size(), is(expectedSizeOfList));
        assertTrue(result.contains(datei1));
        assertTrue(result.contains(datei2));
        assertTrue(result.contains(datei3));
        assertTrue(result.contains(datei4));
    }

    @Test
    public void vonDatumFilter() {
        Suche suche = new Suche(
                "20.02.2020",
                "31.12.2100",
                null,
                null,
                null,
                null,
                null);

        List<Datei> result = suchService.starteSuche(suche, "Peter");

        final int expectedSizeOfList = 1;
        assertThat(result.size(), is(expectedSizeOfList));
        assertTrue(result.contains(datei4));
    }

    @Test
    public void bisDatumFilter() {
        Suche suche = new Suche(
                "01.01.2000",
                "20.02.2020",
                null,
                null,
                null,
                null,
                null);

        List<Datei> result = suchService.starteSuche(suche, "Peter");

        final int expectedSizeOfList = 3;
        assertThat(result.size(), is(expectedSizeOfList));
        assertTrue(result.contains(datei1));
        assertTrue(result.contains(datei2));
        assertTrue(result.contains(datei3));
    }

    @Test
    public void einNichtVergebenerTagFilter() {
        String[] tagArray = {"NichtVergebenerTag"};
        Suche suche = new Suche(
                "01.01.2000",
                "31.12.2100",
                tagArray,
                null,
                null,
                null,
                null);

        List<Datei> result = suchService.starteSuche(suche, "Peter");

        final int expectedSizeOfList = 0;
        assertThat(result.size(), is(expectedSizeOfList));
    }

    @Test
    public void einVergebenerTagFilter() {
        String[] tagArray = {"Vorlesung"};
        Suche suche = new Suche(
                "01.01.2000",
                "31.12.2100",
                tagArray,
                null,
                null,
                null,
                null);

        List<Datei> result = suchService.starteSuche(suche, "Peter");

        final int expectedSizeOfList = 2;
        assertThat(result.size(), is(expectedSizeOfList));
        assertTrue(result.contains(datei1));
        assertTrue(result.contains(datei2));
    }

    @Test
    public void mehrereVergebeneTagsFilter() {
        String[] tagArray = {"Relevant", "Irgendwas"};
        Suche suche = new Suche(
                "01.01.2000",
                "31.12.2100",
                tagArray,
                null,
                null,
                null,
                null);

        List<Datei> result = suchService.starteSuche(suche, "Peter");

        final int expectedSizeOfList = 3;
        assertThat(result.size(), is(expectedSizeOfList));
        assertTrue(result.contains(datei1));
        assertTrue(result.contains(datei3));
        assertTrue(result.contains(datei4));
    }

    @Test
    public void mehrereVergebeneTagsVerschiedeneDateienFilter() {
        String[] tagArray = {"Relevant", "Irgendwas", "Vorlesung"};
        Suche suche = new Suche(
                "01.01.2000",
                "31.12.2100",
                tagArray,
                null,
                null,
                null,
                null);

        List<Datei> result = suchService.starteSuche(suche, "Peter");

        final int expectedSizeOfList = 1;
        assertThat(result.size(), is(expectedSizeOfList));
        assertTrue(result.contains(datei1));
    }

    @Test
    public void vergebeneUndNichtVergebeneTagsFilter() {
        String[] tagArray = {"Relevant", "Haus"};
        Suche suche = new Suche(
                "01.01.2000",
                "31.12.2100",
                tagArray,
                null,
                null,
                null,
                null);

        List<Datei> result = suchService.starteSuche(suche, "Peter");

        final int expectedSizeOfList = 0;
        assertThat(result.size(), is(expectedSizeOfList));
    }
}
