package de.hhu.propra2.material2.mops.domain.services;

import de.hhu.propra2.material2.mops.domain.models.Datei;
import de.hhu.propra2.material2.mops.domain.models.Gruppe;
import de.hhu.propra2.material2.mops.domain.models.Tag;
import de.hhu.propra2.material2.mops.domain.models.User;

import java.io.File;
import java.util.Date;
import java.util.List;

public class UploadService {
    private void dateiHochladen(final Datei datei) {
        throw new UnsupportedOperationException("not jet implemented");
    }

    public void dateiHochladen(final File file, final String dateiname,
                               final User user,
                               final Gruppe gruppe,
                               final Date veroeffentlichungsdatum,
                               final List<Tag> tags) {


        if (dateiname != null) {
            throw new UnsupportedOperationException("not jet implemented");
        }
        final String fileName = file.getName();
        final double dateigroesse = file.length() / 1000.0;

        //TODO change id handling
        Datei datei = new Datei(1, "test.pdf", null, user, tags,
                new Date(), veroeffentlichungsdatum, dateigroesse, "pdf");

    }

}
