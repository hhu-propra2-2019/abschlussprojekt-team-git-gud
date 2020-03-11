package de.hhu.propra2.material2.mops.domain.services;

import de.hhu.propra2.material2.mops.domain.models.Datei;
import de.hhu.propra2.material2.mops.domain.models.Tag;
import de.hhu.propra2.material2.mops.domain.models.User;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.mockito.Mockito.mock;

public class UploadServiceTest {

    private UploadService uploadService;

    @Test
    public void dateiWirdHochgeladen() {
        final Tag tag1 = new Tag(1, "tag1");
        final Tag tag2 = new Tag(2, "tag2");
        final Tag tag3 = new Tag(3, "tag3");

        final List<Tag> tags = new ArrayList<>();
        tags.add(tag1);
        tags.add(tag2);
        tags.add(tag3);

        User userMock = mock(User.class);
        Date uploadDatum = new Date();
        Date veroeffentlichungsdatum = new Date();
        final long dateiegroesse = 2250;

        Datei datei = new Datei(1, "test.pdf", null, userMock, tags,
                uploadDatum, veroeffentlichungsdatum, dateiegroesse, "pdf");

        //uploadService.dateiHochladen(datei);
    }
}
