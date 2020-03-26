package de.hhu.propra2.material2.mops.domain.services;

import de.hhu.propra2.material2.mops.domain.models.Datei;
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
import static org.hamcrest.Matchers.anyOf;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockitoExtension.class)
public class SuchServiceTest {

    @Mock
    private User uploaderMock1;
    @Mock
    private User uploaderMock2;

    private SuchService suchService;
    private Datei datei1;
    private Datei datei2;
    private Datei datei3;
    private Datei datei4;
    private List<Datei> dateien;


    /**
     * setUP: SetUp needed for each test.
     */
    @BeforeEach
    @SuppressWarnings("checkstyle:magicnumber")
    public void setUp() {
        this.suchService = new SuchService();

        //Date for Datei
        LocalDate date1 = LocalDate.of(2020, 1, 3);
        LocalDate date2 = LocalDate.of(2020, 3, 5);
        LocalDate uploadDate = LocalDate.of(2000, 1, 1);

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
        datei1 = new Datei(1, "My stuff", uploaderMock1, tags1,
                uploadDate, date1, 1, "pdf", "Uebung");
        datei2 = new Datei(2, "Something", uploaderMock2, tags2,
                uploadDate, date2, 1, "pdf", "Uebung");
        datei3 = new Datei(3, "Insert here", uploaderMock1, tags3,
                uploadDate, date1, 1, "jpg", "Uebung");
        datei4 = new Datei(4, "This datei", uploaderMock2, tags3,
                uploadDate, date2, 1, "jpg", "Uebung");

        dateien = new ArrayList<>(Arrays.asList(datei1, datei2, datei3, datei4));
    }

    @Test
    @SuppressWarnings("checkstyle:magicnumber")
    public void keineDateien() {
        Suche suche = new Suche(
                "",
                "",
                "",
                "",
                "",
                null,
                null,
                "",
                null);

        List<Datei> result = suchService.starteSuche(suche, new ArrayList<>());

        final int expectedSizeOfList = 0;
        assertThat(result.size(), is(expectedSizeOfList));
    }

    @Test
    public void keineFilter() {
        Suche suche = new Suche(
                "",
                "",
                "",
                "",
                "",
                null,
                null,
                "",
                null);

        List<Datei> result = suchService.starteSuche(suche, dateien);

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
                "",
                "",
                "",
                null,
                null,
                "",
                null);

        List<Datei> result = suchService.starteSuche(suche, dateien);

        final int expectedSizeOfList = 2;
        assertThat(result.size(), is(expectedSizeOfList));
        assertTrue(result.contains(datei2));
        assertTrue(result.contains(datei4));
    }

    @Test
    public void bisDatumFilter() {
        Suche suche = new Suche(
                "",
                "2020-02-20",
                "",
                "",
                "",
                null,
                null,
                "",
                null);

        List<Datei> result = suchService.starteSuche(suche, dateien);

        final int expectedSizeOfList = 2;
        assertThat(result.size(), is(expectedSizeOfList));
        assertTrue(result.contains(datei1));
        assertTrue(result.contains(datei3));
    }

    @Test
    public void einNichtVergebenerTagFilter() {
        String tags = "NichtVergebenerTag";
        Suche suche = new Suche(
                "",
                "",
                tags,
                "",
                "",
                null,
                null,
                "",
                null);

        List<Datei> result = suchService.starteSuche(suche, dateien);

        final int expectedSizeOfList = 0;
        assertThat(result.size(), is(expectedSizeOfList));
    }

    @Test
    public void einVergebenerTagFilter() {
        String tags = "Vorlesung";
        Suche suche = new Suche(
                "",
                "",
                tags,
                "",
                "",
                null,
                null,
                "",
                null);

        List<Datei> result = suchService.starteSuche(suche, dateien);

        final int expectedSizeOfList = 2;
        assertThat(result.size(), is(expectedSizeOfList));
        assertTrue(result.contains(datei1));
        assertTrue(result.contains(datei2));
    }

    @Test
    public void mehrereVergebeneTagsFilter() {
        String tags = "Relevant, Irgendwas";
        Suche suche = new Suche(
                "",
                "",
                tags,
                "",
                "",
                null,
                null,
                "",
                null);

        List<Datei> result = suchService.starteSuche(suche, dateien);

        final int expectedSizeOfList = 3;
        assertThat(result.size(), is(expectedSizeOfList));
        assertTrue(result.contains(datei1));
        assertTrue(result.contains(datei3));
        assertTrue(result.contains(datei4));
    }

    @Test
    public void mehrereVergebeneTagsVerschiedeneDateienFilter() {
        String tags = "Relevant, Irgendwas, Vorlesung";
        Suche suche = new Suche(
                "",
                "",
                tags,
                "",
                "",
                null,
                null,
                "",
                null);

        List<Datei> result = suchService.starteSuche(suche, dateien);

        final int expectedSizeOfList = 1;
        assertThat(result.size(), is(expectedSizeOfList));
        assertTrue(result.contains(datei1));
    }

    @Test
    public void vergebeneUndNichtVergebeneTagsFilter() {
        String tags = "Relevant, Haus";
        Suche suche = new Suche(
                "",
                "",
                tags,
                "",
                "",
                null,
                null,
                "",
                null);

        List<Datei> result = suchService.starteSuche(suche, dateien);

        final int expectedSizeOfList = 0;
        assertThat(result.size(), is(expectedSizeOfList));
    }

    @Test
    public void nichtVergebenerDateiTypFilter() {
        String dateiTypen = "docx";
        Suche suche = new Suche(
                "",
                "",
                "",
                dateiTypen,
                "",
                "",
                null,
                "",
                null);

        List<Datei> result = suchService.starteSuche(suche, dateien);

        final int expectedSizeOfList = 0;
        assertThat(result.size(), is(expectedSizeOfList));
    }

    @Test
    public void einDateiTypFilter() {
        String dateiTypen = "pdf";
        Suche suche = new Suche(
                "",
                "",
                "",
                dateiTypen,
                "",
                "",
                null,
                "",
                null);

        List<Datei> result = suchService.starteSuche(suche, dateien);

        final int expectedSizeOfList = 2;
        assertThat(result.size(), is(expectedSizeOfList));
        assertTrue(result.contains(datei1));
        assertTrue(result.contains(datei2));
    }

    @Test
    public void mehrereDateiTypenFilter() {
        String dateiTypen = "pdf, jpg";
        Suche suche = new Suche(
                "",
                "",
                "",
                dateiTypen,
                "",
                "",
                null,
                "",
                null);

        List<Datei> result = suchService.starteSuche(suche, dateien);

        final int expectedSizeOfList = 4;
        assertThat(result.size(), is(expectedSizeOfList));
        assertTrue(result.contains(datei1));
        assertTrue(result.contains(datei2));
        assertTrue(result.contains(datei3));
        assertTrue(result.contains(datei4));
    }

    @Test
    public void nichtVorhandenerUploaderFilter() {
        String uploader = "See";
        Suche suche = new Suche(
                "",
                "",
                "",
                "",
                uploader,
                "",
                null,
                "",
                null);

        List<Datei> result = suchService.starteSuche(suche, dateien);

        final int expectedSizeOfList = 0;
        assertThat(result.size(), is(expectedSizeOfList));
    }

    @Test
    public void einVorhandenerUploaderFilter() {
        String uploader = "Baum";
        Suche suche = new Suche(
                "",
                "",
                "",
                "",
                uploader,
                "",
                null,
                "",
                null);

        List<Datei> result = suchService.starteSuche(suche, dateien);

        final int expectedSizeOfList = 2;
        assertThat(result.size(), is(expectedSizeOfList));
        assertTrue(result.contains(datei1));
        assertTrue(result.contains(datei3));
    }

    @Test
    public void mehrereUploaderFilter() {
        String uploader = "Baum, Stein";
        Suche suche = new Suche(
                "",
                "",
                "",
                "",
                uploader,
                "",
                null,
                "",
                null);

        List<Datei> result = suchService.starteSuche(suche, dateien);

        final int expectedSizeOfList = 4;
        assertThat(result.size(), is(expectedSizeOfList));
        assertTrue(result.contains(datei1));
        assertTrue(result.contains(datei2));
        assertTrue(result.contains(datei3));
        assertTrue(result.contains(datei4));
    }

    @Test
    @SuppressWarnings("checkstyle:magicnumber")
    public void sortierungDateiNameAufsteigend() {
        Suche suche = new Suche(
                "",
                "",
                "",
                "",
                "",
                "Name",
                null,
                "",
                null);

        List<Datei> result = suchService.starteSuche(suche, dateien);

        final int expectedSizeOfList = 4;
        assertThat(result.size(), is(expectedSizeOfList));
        assertEquals(result.get(0), datei3);
        assertEquals(result.get(1), datei1);
        assertEquals(result.get(2), datei2);
        assertEquals(result.get(3), datei4);
    }

    @Test
    @SuppressWarnings("checkstyle:magicnumber")
    public void sortierungDateiNameAbsteigend() {
        Suche suche = new Suche(
                "",
                "",
                "",
                "",
                "",
                "Name",
                null,
                "",
                "absteigend");

        List<Datei> result = suchService.starteSuche(suche, dateien);

        final int expectedSizeOfList = 4;
        assertThat(result.size(), is(expectedSizeOfList));
        assertEquals(result.get(0), datei4);
        assertEquals(result.get(1), datei2);
        assertEquals(result.get(2), datei1);
        assertEquals(result.get(3), datei3);
    }

    @Test
    @SuppressWarnings("checkstyle:magicnumber")
    public void sortierungDateiTypAufsteigend() {
        Suche suche = new Suche(
                "",
                "",
                "",
                "",
                "",
                "Dateityp",
                null,
                "",
                null);

        List<Datei> result = suchService.starteSuche(suche, dateien);

        final int expectedSizeOfList = 4;
        assertThat(result.size(), is(expectedSizeOfList));
        assertTrue(result.contains(datei1));
        assertTrue(result.contains(datei2));
        assertTrue(result.contains(datei3));
        assertTrue(result.contains(datei4));
        assertThat(result.get(0), anyOf(equalTo(datei3), equalTo(datei4)));
        assertThat(result.get(1), anyOf(equalTo(datei3), equalTo(datei4)));
        assertThat(result.get(2), anyOf(equalTo(datei1), equalTo(datei2)));
        assertThat(result.get(3), anyOf(equalTo(datei1), equalTo(datei2)));
    }

    @Test
    @SuppressWarnings("checkstyle:magicnumber")
    public void sortierungDateiTypAbsteigend() {
        Suche suche = new Suche(
                "",
                "",
                "",
                "",
                "",
                "Dateityp",
                null,
                "",
                "absteigend");

        List<Datei> result = suchService.starteSuche(suche, dateien);

        final int expectedSizeOfList = 4;
        assertThat(result.size(), is(expectedSizeOfList));
        assertTrue(result.contains(datei1));
        assertTrue(result.contains(datei2));
        assertTrue(result.contains(datei3));
        assertTrue(result.contains(datei4));
        assertThat(result.get(0), anyOf(equalTo(datei1), equalTo(datei2)));
        assertThat(result.get(1), anyOf(equalTo(datei1), equalTo(datei2)));
        assertThat(result.get(2), anyOf(equalTo(datei3), equalTo(datei4)));
        assertThat(result.get(3), anyOf(equalTo(datei3), equalTo(datei4)));
    }

    @Test
    @SuppressWarnings("checkstyle:magicnumber")
    public void sortierungUploaderAufsteigend() {
        Suche suche = new Suche(
                "",
                "",
                "",
                "",
                "",
                "Uploader",
                null,
                "",
                null);

        List<Datei> result = suchService.starteSuche(suche, dateien);

        final int expectedSizeOfList = 4;
        assertThat(result.size(), is(expectedSizeOfList));
        assertTrue(result.contains(datei1));
        assertTrue(result.contains(datei2));
        assertTrue(result.contains(datei3));
        assertTrue(result.contains(datei4));
        assertThat(result.get(0), anyOf(equalTo(datei1), equalTo(datei3)));
        assertThat(result.get(1), anyOf(equalTo(datei1), equalTo(datei3)));
        assertThat(result.get(2), anyOf(equalTo(datei2), equalTo(datei4)));
        assertThat(result.get(3), anyOf(equalTo(datei2), equalTo(datei4)));
    }

    @Test
    @SuppressWarnings("checkstyle:magicnumber")
    public void sortierungUploaderAbsteigend() {
        Suche suche = new Suche(
                "",
                "",
                "",
                "",
                "",
                "Uploader",
                null,
                "",
                "absteigend");

        List<Datei> result = suchService.starteSuche(suche, dateien);

        final int expectedSizeOfList = 4;
        assertThat(result.size(), is(expectedSizeOfList));
        assertTrue(result.contains(datei1));
        assertTrue(result.contains(datei2));
        assertTrue(result.contains(datei3));
        assertTrue(result.contains(datei4));
        assertThat(result.get(0), anyOf(equalTo(datei2), equalTo(datei4)));
        assertThat(result.get(1), anyOf(equalTo(datei2), equalTo(datei4)));
        assertThat(result.get(2), anyOf(equalTo(datei1), equalTo(datei3)));
        assertThat(result.get(3), anyOf(equalTo(datei1), equalTo(datei3)));
    }

    @Test
    @SuppressWarnings("checkstyle:magicnumber")
    public void sortierungDatumAufsteigend() {
        Suche suche = new Suche(
                "",
                "",
                "",
                "",
                "",
                "Datum",
                null,
                "",
                null);

        List<Datei> result = suchService.starteSuche(suche, dateien);

        final int expectedSizeOfList = 4;
        assertThat(result.size(), is(expectedSizeOfList));
        assertTrue(result.contains(datei1));
        assertTrue(result.contains(datei2));
        assertTrue(result.contains(datei3));
        assertTrue(result.contains(datei4));
        assertThat(result.get(0), anyOf(equalTo(datei1), equalTo(datei3)));
        assertThat(result.get(1), anyOf(equalTo(datei1), equalTo(datei3)));
        assertThat(result.get(2), anyOf(equalTo(datei2), equalTo(datei4)));
        assertThat(result.get(3), anyOf(equalTo(datei2), equalTo(datei4)));
    }

    @Test
    @SuppressWarnings("checkstyle:magicnumber")
    public void sortierungDatumAbsteigend() {
        Suche suche = new Suche(
                "",
                "",
                "",
                "",
                "",
                "Datum",
                null,
                "",
                "absteigend");

        List<Datei> result = suchService.starteSuche(suche, dateien);

        final int expectedSizeOfList = 4;
        assertThat(result.size(), is(expectedSizeOfList));
        assertTrue(result.contains(datei1));
        assertTrue(result.contains(datei2));
        assertTrue(result.contains(datei3));
        assertTrue(result.contains(datei4));
        assertThat(result.get(0), anyOf(equalTo(datei2), equalTo(datei4)));
        assertThat(result.get(1), anyOf(equalTo(datei2), equalTo(datei4)));
        assertThat(result.get(2), anyOf(equalTo(datei1), equalTo(datei3)));
        assertThat(result.get(3), anyOf(equalTo(datei1), equalTo(datei3)));
    }
}
