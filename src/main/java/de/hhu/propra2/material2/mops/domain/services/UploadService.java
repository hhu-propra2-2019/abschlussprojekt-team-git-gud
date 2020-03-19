package de.hhu.propra2.material2.mops.domain.services;

import com.google.common.base.Strings;
import de.hhu.propra2.material2.mops.Database.DTOs.UserDTO;
import de.hhu.propra2.material2.mops.Database.Repository;
import de.hhu.propra2.material2.mops.Exceptions.NoUploadPermissionException;
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
     * @param file                    The file to upload.
     * @param newFileName             The name the file is saved with. If null the original file name is used
     * @param user                    The user who is saved as uploader.
     * @param gruppe                  The group in which the file is saved.
     * @param veroeffentlichungsdatum The date which controls the availability of the file. If null the file is direct
     *                                available after upload.
     * @param tags                    The tags for the file
     * @return A Datei object which represents the saved File
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

        Datei datei = new Datei(1, fileName, user, tags,
                LocalDate.now(), veroeffentlichungsdatum, file.getSize(), fileExtension, null);
        long dateiId = modelService.saveDatei(datei, gruppe);

        if (!fileUploadService.upload(file, String.valueOf(dateiId))) {
            throw new FileUploadException();
        }
        return new Datei(dateiId, datei.getName(), datei.getUploader(), datei.getTags(),
                datei.getUploaddatum(), datei.getVeroeffentlichungsdatum(), datei.getDateigroesse(),
                datei.getDateityp(), datei.getKategorie());
    }

    /**
     * starts upload.
     */
    @Override
    public void startUpload(final UploadForm upForm, final String uploader) throws NoUploadPermissionException,
            SQLException, FileUploadException {
        UserDTO userDTO = repository.findUserByKeycloakname(uploader);
        User user = modelService.loadUser(userDTO);

        Gruppe gruppe = user.getGruppeById(upForm.getGruppenId());

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
        ArrayList<String> tagTexts = Arrays.stream(tagStrings.split(","))
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
