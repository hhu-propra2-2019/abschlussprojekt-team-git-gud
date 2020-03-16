package de.hhu.propra2.material2.mops.domain.services;

import de.hhu.propra2.material2.mops.Database.DTOs.UserDTO;
import de.hhu.propra2.material2.mops.Database.Repository;
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

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class SuchServiceTest {

    @Mock
    private ModelService modelServiceMock;
    @Mock
    private User userMock;
    @Mock
    private User uploaderMock1;
    @Mock
    private User uploaderMock2;
    @Mock
    private Repository repositoryMock;

    private SuchService suchService;
    private Gruppe gruppe1;
    private Gruppe gruppe2;
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
        this.suchService = new SuchService(modelServiceMock, repositoryMock);

        Mockito.lenient().when(modelServiceMock.loadUser(any(UserDTO.class))).thenReturn(userMock);
        Mockito.lenient().when(modelServiceMock.loadUser(null)).thenReturn(userMock);

        //Date for Datei
        LocalDate date1 = LocalDate.of(2020, 1, 3);
        LocalDate date2 = LocalDate.of(2020, 3, 5);

        //Tags for Datei
        Tag tag1 = new Tag(1, "Vorlesung");
        Tag tag2 = new Tag(2, "Relevant");
        Tag tag3 = new Tag(3, "Übung");
        Tag tag4 = new Tag(4, "Irgendwas");
        List<Tag> tags1 = new ArrayList<>(Arrays.asList(tag1, tag2, tag3, tag4));
        List<Tag> tags2 = new ArrayList<>(Arrays.asList(tag1));
        List<Tag> tags3 = new ArrayList<>(Arrays.asList(tag2, tag4));

        //Uploader for Datei
        Mockito.lenient().when(uploaderMock1.getNachname()).thenReturn("Baum");
        Mockito.lenient().when(uploaderMock2.getNachname()).thenReturn("Stein");

        //Dateien for List<Datei>
        datei1 = new Datei(1, "1", "a/b/2", uploaderMock1, tags1,
                date1, date1, 1, "pdf");
        datei2 = new Datei(2, "2", "a/b/2", uploaderMock2, tags2,
                date1, date1, 1, "pdf");
        datei3 = new Datei(3, "3", "a/b/3", uploaderMock1, tags3,
                date1, date1, 1, "jpg");
        datei4 = new Datei(4, "4", "a/b/4", uploaderMock2, tags3,
                date2, date2, 1, "jpg");
        List<Datei> dateienGruppe1 = new ArrayList<>(Arrays.asList(datei1, datei2, datei3));
        List<Datei> dateienGruppe2 = new ArrayList<>(Arrays.asList(datei4));

        gruppe1 = new Gruppe(1, "1", dateienGruppe1);
        gruppe2 = new Gruppe(2, "2", dateienGruppe2);
        Mockito.lenient().when(userMock.getAllGruppen()).thenReturn(Arrays.asList(gruppe1, gruppe2));
        Mockito.lenient().when(modelServiceMock.getAlleDateienByGruppeId(1L)).thenReturn(dateienGruppe1);
        Mockito.lenient().when(modelServiceMock.getAlleDateienByGruppeId(2L)).thenReturn(dateienGruppe2);
    }

    @Test
    @SuppressWarnings("checkstyle:magicnumber")
    public void keineDateienInGruppen() {
        Gruppe gruppe3 = new Gruppe(3, "3", new ArrayList<>());
        Gruppe gruppe4 = new Gruppe(4, "4", new ArrayList<>());
        when(userMock.getAllGruppen()).thenReturn(Arrays.asList(gruppe3, gruppe4));
        Suche suche = new Suche(
                "",
                "",
                null,
                null,
                null,
                null,
                null,
                "",
                null);

        List<Datei> result = suchService.starteSuche(suche, "Peter");

        final int expectedSizeOfList = 0;
        assertThat(result.size(), is(expectedSizeOfList));
    }

    @Test
    public void keineFilter() {
        Suche suche = new Suche(
                "",
                "",
                null,
                null,
                null,
                null,
                null,
                "",
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
                "2020-02-20",
                "",
                null,
                null,
                null,
                null,
                null,
                "",
                null);

        List<Datei> result = suchService.starteSuche(suche, "Peter");

        final int expectedSizeOfList = 1;
        assertThat(result.size(), is(expectedSizeOfList));
        assertTrue(result.contains(datei4));
    }

    @Test
    public void bisDatumFilter() {
        Suche suche = new Suche(
                "",
                "2020-02-20",
                null,
                null,
                null,
                null,
                null,
                "",
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
        String[] tags = {"NichtVergebenerTag"};
        Suche suche = new Suche(
                "",
                "",
                tags,
                null,
                null,
                null,
                null,
                "",
                null);

        List<Datei> result = suchService.starteSuche(suche, "Peter");

        final int expectedSizeOfList = 0;
        assertThat(result.size(), is(expectedSizeOfList));
    }

    @Test
    public void einVergebenerTagFilter() {
        String[] tags = {"Vorlesung"};
        Suche suche = new Suche(
                "",
                "",
                tags,
                null,
                null,
                null,
                null,
                "",
                null);

        List<Datei> result = suchService.starteSuche(suche, "Peter");

        final int expectedSizeOfList = 2;
        assertThat(result.size(), is(expectedSizeOfList));
        assertTrue(result.contains(datei1));
        assertTrue(result.contains(datei2));
    }

    @Test
    public void mehrereVergebeneTagsFilter() {
        String[] tags = {"Relevant", "Irgendwas"};
        Suche suche = new Suche(
                "",
                "",
                tags,
                null,
                null,
                null,
                null,
                "",
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
        String[] tags = {"Relevant", "Irgendwas", "Vorlesung"};
        Suche suche = new Suche(
                "",
                "",
                tags,
                null,
                null,
                null,
                null,
                "",
                null);

        List<Datei> result = suchService.starteSuche(suche, "Peter");

        final int expectedSizeOfList = 1;
        assertThat(result.size(), is(expectedSizeOfList));
        assertTrue(result.contains(datei1));
    }

    @Test
    public void vergebeneUndNichtVergebeneTagsFilter() {
        String[] tags = {"Relevant", "Haus"};
        Suche suche = new Suche(
                "",
                "",
                tags,
                null,
                null,
                null,
                null,
                "",
                null);

        List<Datei> result = suchService.starteSuche(suche, "Peter");

        final int expectedSizeOfList = 0;
        assertThat(result.size(), is(expectedSizeOfList));
    }

    @Test
    public void nichtVergebenerDateiTypFilter() {
        String[] dateiTypen = {"docx"};
        Suche suche = new Suche(
                "",
                "",
                null,
                dateiTypen,
                null,
                null,
                null,
                "",
                null);

        List<Datei> result = suchService.starteSuche(suche, "Peter");

        final int expectedSizeOfList = 0;
        assertThat(result.size(), is(expectedSizeOfList));
    }

    @Test
    public void einDateiTypFilter() {
        String[] dateiTypen = {"pdf"};
        Suche suche = new Suche(
                "",
                "",
                null,
                dateiTypen,
                null,
                null,
                null,
                "",
                null);

        List<Datei> result = suchService.starteSuche(suche, "Peter");

        final int expectedSizeOfList = 2;
        assertThat(result.size(), is(expectedSizeOfList));
        assertTrue(result.contains(datei1));
        assertTrue(result.contains(datei2));
    }

    @Test
    public void mehrereDateiTypenFilter() {
        String[] dateiTypen = {"pdf", "jpg"};
        Suche suche = new Suche(
                "",
                "",
                null,
                dateiTypen,
                null,
                null,
                null,
                "",
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
    public void nichtVorhandenerUploaderFilter() {
        String[] uploader = {"See"};
        Suche suche = new Suche(
                "",
                "",
                null,
                null,
                uploader,
                null,
                null,
                "",
                null);

        List<Datei> result = suchService.starteSuche(suche, "Peter");

        final int expectedSizeOfList = 0;
        assertThat(result.size(), is(expectedSizeOfList));
    }

    @Test
    public void einVorhandenerUploaderFilter() {
        String[] uploader = {"Baum"};
        Suche suche = new Suche(
                "",
                "",
                null,
                null,
                uploader,
                null,
                null,
                "",
                null);

        List<Datei> result = suchService.starteSuche(suche, "Peter");

        final int expectedSizeOfList = 2;
        assertThat(result.size(), is(expectedSizeOfList));
        assertTrue(result.contains(datei1));
        assertTrue(result.contains(datei3));
    }

    @Test
    public void mehrereUploaderFilter() {
        String[] uploader = {"Baum", "Stein"};
        Suche suche = new Suche(
                "",
                "",
                null,
                null,
                uploader,
                null,
                null,
                "",
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
    public void gruppeFilter() {
        Suche suche = new Suche(
                "",
                "",
                null,
                null,
                null,
                null,
                1L,
                "",
                null);

        List<Datei> result = suchService.starteSuche(suche, "Peter");

        final int expectedSizeOfList = 3;
        assertThat(result.size(), is(expectedSizeOfList));
        assertTrue(result.contains(datei1));
        assertTrue(result.contains(datei2));
        assertTrue(result.contains(datei3));
    }
}
