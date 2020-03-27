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
import java.util.Collections;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.anyOf;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockitoExtension.class)
class SuchServiceTest {

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
    void setUp() {
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
        List<Tag> tags2 = new ArrayList<>(Collections.singletonList(tag1));
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
    void keineDateien() {
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

        List<Datei> result = suchService.starteSuche(suche, new ArrayList<>());

        final int expectedSizeOfList = 0;
        assertThat(result.size(), is(expectedSizeOfList));
    }

    @Test
    void keineFilter() {
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

        List<Datei> result = suchService.starteSuche(suche, dateien);

        final int expectedSizeOfList = 4;
        assertThat(result.size(), is(expectedSizeOfList));
        assertTrue(result.contains(datei1));
        assertTrue(result.contains(datei2));
        assertTrue(result.contains(datei3));
        assertTrue(result.contains(datei4));
    }

    @Test
    void vonDatumFilter() {
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

        List<Datei> result = suchService.starteSuche(suche, dateien);

        final int expectedSizeOfList = 2;
        assertThat(result.size(), is(expectedSizeOfList));
        assertTrue(result.contains(datei2));
        assertTrue(result.contains(datei4));
    }

    @Test
    void bisDatumFilter() {
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

        List<Datei> result = suchService.starteSuche(suche, dateien);

        final int expectedSizeOfList = 2;
        assertThat(result.size(), is(expectedSizeOfList));
        assertTrue(result.contains(datei1));
        assertTrue(result.contains(datei3));
    }

    @Test
    void einNichtVergebenerTagFilter() {
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

        List<Datei> result = suchService.starteSuche(suche, dateien);

        final int expectedSizeOfList = 0;
        assertThat(result.size(), is(expectedSizeOfList));
    }

    @Test
    void einVergebenerTagFilter() {
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

        List<Datei> result = suchService.starteSuche(suche, dateien);

        final int expectedSizeOfList = 2;
        assertThat(result.size(), is(expectedSizeOfList));
        assertTrue(result.contains(datei1));
        assertTrue(result.contains(datei2));
    }

    @Test
    void mehrereVergebeneTagsFilter() {
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

        List<Datei> result = suchService.starteSuche(suche, dateien);

        final int expectedSizeOfList = 3;
        assertThat(result.size(), is(expectedSizeOfList));
        assertTrue(result.contains(datei1));
        assertTrue(result.contains(datei3));
        assertTrue(result.contains(datei4));
    }

    @Test
    void mehrereVergebeneTagsVerschiedeneDateienFilter() {
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

        List<Datei> result = suchService.starteSuche(suche, dateien);

        final int expectedSizeOfList = 1;
        assertThat(result.size(), is(expectedSizeOfList));
        assertTrue(result.contains(datei1));
    }

    @Test
    void vergebeneUndNichtVergebeneTagsFilter() {
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

        List<Datei> result = suchService.starteSuche(suche, dateien);

        final int expectedSizeOfList = 0;
        assertThat(result.size(), is(expectedSizeOfList));
    }

    @Test
    void nichtVergebenerDateiTypFilter() {
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

        List<Datei> result = suchService.starteSuche(suche, dateien);

        final int expectedSizeOfList = 0;
        assertThat(result.size(), is(expectedSizeOfList));
    }

    @Test
    void einDateiTypFilter() {
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

        List<Datei> result = suchService.starteSuche(suche, dateien);

        final int expectedSizeOfList = 2;
        assertThat(result.size(), is(expectedSizeOfList));
        assertTrue(result.contains(datei1));
        assertTrue(result.contains(datei2));
    }

    @Test
    void mehrereDateiTypenFilter() {
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

        List<Datei> result = suchService.starteSuche(suche, dateien);

        final int expectedSizeOfList = 4;
        assertThat(result.size(), is(expectedSizeOfList));
        assertTrue(result.contains(datei1));
        assertTrue(result.contains(datei2));
        assertTrue(result.contains(datei3));
        assertTrue(result.contains(datei4));
    }

    @Test
    void nichtVorhandenerUploaderFilter() {
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

        List<Datei> result = suchService.starteSuche(suche, dateien);

        final int expectedSizeOfList = 0;
        assertThat(result.size(), is(expectedSizeOfList));
    }

    @Test
    void einVorhandenerUploaderFilter() {
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

        List<Datei> result = suchService.starteSuche(suche, dateien);

        final int expectedSizeOfList = 2;
        assertThat(result.size(), is(expectedSizeOfList));
        assertTrue(result.contains(datei1));
        assertTrue(result.contains(datei3));
    }

    @Test
    void mehrereUploaderFilter() {
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
    void sortierungDateiNameAufsteigend() {
        Suche suche = new Suche(
                "",
                "",
                null,
                null,
                null,
                "name",
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
    void sortierungDateiNameAbsteigend() {
        Suche suche = new Suche(
                "",
                "",
                null,
                null,
                null,
                "name",
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
    void sortierungDateiTypAufsteigend() {
        Suche suche = new Suche(
                "",
                "",
                null,
                null,
                null,
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
    void sortierungDateiTypAbsteigend() {
        Suche suche = new Suche(
                "",
                "",
                null,
                null,
                null,
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
    void sortierungUploaderAufsteigend() {
        Suche suche = new Suche(
                "",
                "",
                null,
                null,
                null,
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
    void sortierungUploaderAbsteigend() {
        Suche suche = new Suche(
                "",
                "",
                null,
                null,
                null,
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
    void sortierungDatumAufsteigend() {
        Suche suche = new Suche(
                "",
                "",
                null,
                null,
                null,
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
    void sortierungDatumAbsteigend() {
        Suche suche = new Suche(
                "",
                "",
                null,
                null,
                null,
                null,
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
    public void onlyFilesWithCorrectVeroeffentlichungsDatumAreShown() {
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

        LocalDate yesterday = LocalDate.now().minusDays(1);
        LocalDate tomorow = LocalDate.now().plusDays(1);
        LocalDate today = LocalDate.now();

        Datei dateiYesterday = new Datei(1, "My stuff", uploaderMock1, null,
                null, yesterday, 1, "pdf", "Uebung");
        Datei dateiTomorrow = new Datei(1, "My stuff", uploaderMock1, null,
                null, tomorow, 1, "pdf", "Uebung");
        Datei dateiToday = new Datei(1, "My stuff", uploaderMock1, null,
                null, today, 1, "pdf", "Uebung");

        List<Datei> dateienMitVeroeffentlichungsdatum = new ArrayList<>();
        dateienMitVeroeffentlichungsdatum.add(dateiYesterday);
        dateienMitVeroeffentlichungsdatum.add(dateiToday);
        dateienMitVeroeffentlichungsdatum.add(dateiTomorrow);

        List<Datei> result = suchService.starteSuche(suche, dateienMitVeroeffentlichungsdatum);

        assertThat(result.contains(dateiToday), is(true));
        assertThat(result.contains(dateiTomorrow), is(false));
        assertThat(result.contains(dateiYesterday), is(true));
    }
}
