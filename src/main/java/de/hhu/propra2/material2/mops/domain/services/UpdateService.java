package de.hhu.propra2.material2.mops.domain.services;

import com.google.common.base.Strings;
import de.hhu.propra2.material2.mops.database.DTOs.UserDTO;
import de.hhu.propra2.material2.mops.database.Repository;
import de.hhu.propra2.material2.mops.Exceptions.NoUploadPermissionException;
import de.hhu.propra2.material2.mops.domain.models.Datei;
import de.hhu.propra2.material2.mops.domain.models.Gruppe;
import de.hhu.propra2.material2.mops.domain.models.Tag;
import de.hhu.propra2.material2.mops.domain.models.UpdateForm;
import de.hhu.propra2.material2.mops.domain.models.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UpdateService implements IUpdateService {

    private final Repository repository;
    private final ModelService modelService;

    public UpdateService(final Repository repositoryArg,
                         final ModelService modelServiceArg) {
        this.repository = repositoryArg;
        this.modelService = modelServiceArg;
    }

    /**
     * @param dateiId                 The id of the file which should be updated
     * @param veroeffentlichungsdatum The new date which controls the availability of the file.
     *                                If null the file is direct available after upload.
     * @param tags                    The new tags for the file
     * @return A Datei object which represents the saved File
     */
    public Datei dateiUpdate(final Long dateiId, final Long gruppenId, final LocalDate veroeffentlichungsdatum,
                             final List<Tag> tags) throws SQLException {
        Datei datei = modelService.getDateiById(dateiId);
        Datei changedDatei = new Datei(dateiId, datei.getName(), datei.getUploader(),
                tags == null ? new ArrayList<>() : tags,
                datei.getUploaddatum(), veroeffentlichungsdatum, datei.getDateigroesse(),
                datei.getDateityp(), datei.getKategorie());

        modelService.saveDatei(changedDatei, gruppenId);

        return changedDatei;
    }

    /**
     * for Update of file.
     *
     * @param upForm           Update Form
     * @param keycloakUserName KeycloakName of the User
     * @param gruppenId        Id of the group where the file is saved
     * @param dateiId          The id of the file
     * @throws SQLException    if user cannot be found
     * @throws NoUploadPermissionException if user has no update/access permission
     */
    @Override
    @Transactional
    public void startUpdate(final UpdateForm upForm,
                            final String keycloakUserName,
                            final Long gruppenId,
                            final Long dateiId)
            throws SQLException, NoUploadPermissionException {

        UserDTO userDTO = repository.findUserByKeycloakname(keycloakUserName);
        User user = modelService.loadUser(userDTO);

        Gruppe gruppe = user.getGruppeById(gruppenId);

        if (!user.hasUploadPermission(gruppe)) {
            throw new NoUploadPermissionException("User has no update permission");
        }

        if (!user.hasFileAccessPermission(gruppenId, dateiId)) {
            throw new NoUploadPermissionException("User has no access permission.");
        }

        dateiUpdate(dateiId,
                gruppenId,
                parseStringToDate(upForm.getTimedUpload()),
                convertSeperatedStringToList(upForm.getSelectedTags()));
    }

    /**
     * checks if user has access to given file in given group.
     *
     * @param keycloakUserName the keycloakName of the user
     * @param gruppenId        id of the group
     * @param dateiId          id of the file
     * @return true if user has access to the file and the group, false if not
     * @throws SQLException if user cannot be found in DB
     */
    public boolean hasAccessPermissionForUpdate(final String keycloakUserName,
                                                final Long gruppenId,
                                                final Long dateiId) throws SQLException {

        UserDTO userDTO = repository.findUserByKeycloakname(keycloakUserName);
        User user = modelService.loadUser(userDTO);

        return user.hasFileAccessPermission(gruppenId, dateiId);
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
