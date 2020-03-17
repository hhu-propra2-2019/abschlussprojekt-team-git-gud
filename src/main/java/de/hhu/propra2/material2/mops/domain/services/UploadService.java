package de.hhu.propra2.material2.mops.domain.services;

import com.google.common.base.Strings;
import de.hhu.propra2.material2.mops.Database.DTOs.UserDTO;
import de.hhu.propra2.material2.mops.Database.Repository;
import de.hhu.propra2.material2.mops.domain.models.Datei;
import de.hhu.propra2.material2.mops.domain.models.Gruppe;
import de.hhu.propra2.material2.mops.domain.models.Tag;
import de.hhu.propra2.material2.mops.domain.models.UploadForm;
import de.hhu.propra2.material2.mops.domain.models.User;
import org.apache.commons.io.FilenameUtils;
import org.apache.tomcat.util.http.fileupload.FileUploadException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UploadService implements IUploadService {

    private final Repository repository;
    private final ModelService modelService;
    private final FileUploadService fileUploadService;

    public UploadService(final Repository repositoryArg, final ModelService modelServiceArg,
                         final FileUploadService fileUploadServiceArg) {
        this.repository = repositoryArg;
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
                                final LocalDate veroeffentlichungsdatum,
                                final List<Tag> tags) throws FileUploadException, SQLException {
        String fileName = Strings.isNullOrEmpty(newFileName) ? file.getName() : newFileName;
        String fileExtension = FilenameUtils.getExtension(fileName);
        //if the newFileName does not have an extension use the original file extension
        if (Strings.isNullOrEmpty(fileExtension)) {
            fileExtension = FilenameUtils.getExtension(file.getOriginalFilename());
            fileName += "." + fileExtension;
        }

        String objectStoragePath = null;
        try {
            objectStoragePath = fileUploadService.upload(file, newFileName, String.valueOf(gruppe.getId()));
        } catch (Exception e) {
            throw new FileUploadException("Uploading file to minIO causes an error");
        }
        Datei datei = new Datei(1, fileName, objectStoragePath, user, tags,
                LocalDate.now(), veroeffentlichungsdatum, file.getSize(), fileExtension);
        modelService.saveDatei(datei, gruppe);
        return datei;
    }

    /**
     * starts upload.
     */
    @Override
    public void startUpload(final UploadForm upForm, final String uploader) throws NoUploadPermissionException,
            SQLException, FileUploadException {
        UserDTO userDTO = repository.findUserByKeycloakname(uploader);
        User user = modelService.loadUser(userDTO);

        Gruppe gruppe = user.getGroup(upForm.getGruppenwahl());

        if (!user.hasUploadPermission(gruppe)) {
            throw new NoUploadPermissionException("User has no upload permission");
        }

        dateiHochladen(upForm.getDatei(), upForm.getDateiname(), user, gruppe,
                parseStringToDate(upForm.getTimedUpload()), convertSeperatedStringToList(upForm.getSelectedTags()));
    }

    private ArrayList<Tag> convertSeperatedStringToList(final String tagStrings) {
        if (Strings.isNullOrEmpty(tagStrings.trim())) {
            return new ArrayList<>();
        }
        ArrayList<String> tagTexts = Arrays.asList(tagStrings.split(","))
                .stream()
                .map(String::trim)
                .map(String::toLowerCase).collect(Collectors.toCollection(ArrayList::new));

        ArrayList<Tag> tags = new ArrayList<>();
        for (String tagText : tagTexts) {
            tags.add(new Tag(1, tagText));
        }
        return tags;
    }

    private LocalDate parseStringToDate(final String dateString) {
        return Strings.isNullOrEmpty(dateString) ? null : LocalDate.parse(dateString);
    }
}
