package de.hhu.propra2.material2.mops.domain.services;

import com.google.common.base.Strings;
import de.hhu.propra2.material2.mops.domain.models.Datei;
import de.hhu.propra2.material2.mops.domain.models.Gruppe;
import de.hhu.propra2.material2.mops.domain.models.Tag;
import de.hhu.propra2.material2.mops.domain.models.User;
import org.apache.commons.io.FilenameUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
import java.util.List;

@Service
public class UploadService {

    private final ModelService modelService;
    private final FileUploadService fileUploadService;

    public UploadService(final ModelService modelServiceArg, final FileUploadService fileUploadServiceArg) {
        this.modelService = modelServiceArg;
        this.fileUploadService = fileUploadServiceArg;
    }

    /**
     * @param file
     * @param newFileName
     * @param user
     * @param gruppe
     * @param veroeffentlichungsdatum
     * @param tags
     * @return
     * @throws Exception
     */
    @Transactional
    public Datei dateiHochladen(final MultipartFile file, final String newFileName,
                                final User user,
                                final Gruppe gruppe,
                                final Date veroeffentlichungsdatum,
                                final List<Tag> tags) throws Exception {
        String fileName = Strings.isNullOrEmpty(newFileName) ? file.getName() : newFileName;
        String fileExtension = FilenameUtils.getExtension(fileName);
        //if the newFileName does not have an extension use the original file extension
        if (Strings.isNullOrEmpty(fileExtension)) {
            fileExtension = FilenameUtils.getExtension(file.getOriginalFilename());
            fileName += "." + fileExtension;
        }

        String objectStoragePath = fileUploadService.upload(file, newFileName, String.valueOf(gruppe.getId()));
        Datei datei = new Datei(1, fileName, objectStoragePath, user, tags,
                new Date(), veroeffentlichungsdatum, file.getSize(), fileExtension);
        modelService.saveDatei(datei, gruppe);
        return datei;
    }
}
