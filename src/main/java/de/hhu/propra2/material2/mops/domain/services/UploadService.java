package de.hhu.propra2.material2.mops.domain.services;

import de.hhu.propra2.material2.mops.domain.models.Datei;
import de.hhu.propra2.material2.mops.domain.models.Gruppe;
import de.hhu.propra2.material2.mops.domain.models.Tag;
import de.hhu.propra2.material2.mops.domain.models.User;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
import java.util.List;

@Service
public class UploadService {

    @Autowired
    private ModelService modelService;
    @Autowired
    private FileUploadService fileUploadService;

    private void dateiHochladen(final Datei datei) {
        throw new UnsupportedOperationException("not jet implemented");
    }

    @Transactional
    public void oldDateiHochladen(final MultipartFile file, final String dateiname,
                                  final User user,
                                  final Gruppe gruppe,
                                  final Date veroeffentlichungsdatum,
                                  final List<Tag> tags) throws Exception {

        if (dateiname != null) {
            throw new UnsupportedOperationException("not jet implemented");
        }
        final String fileName = file.getName();
        final long dateigroesse = file.getSize();
        final String dateityp = FilenameUtils.getExtension(fileName);

        Datei datei = new Datei(1, fileName, null, user, tags,
                new Date(), veroeffentlichungsdatum, dateigroesse, dateityp);
        modelService.saveDatei(datei, gruppe);
        fileUploadService.upload(file, String.valueOf(gruppe.getId()));
    }
}
