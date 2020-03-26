package de.hhu.propra2.material2.mops.domain.services;

import de.hhu.propra2.material2.mops.Exceptions.NoDeletePermissionException;
import de.hhu.propra2.material2.mops.database.DTOs.DateiDTO;
import de.hhu.propra2.material2.mops.database.DTOs.GruppeDTO;
import de.hhu.propra2.material2.mops.database.DTOs.UserDTO;
import de.hhu.propra2.material2.mops.database.Repository;
import de.hhu.propra2.material2.mops.domain.models.Datei;
import de.hhu.propra2.material2.mops.domain.models.Gruppe;
import de.hhu.propra2.material2.mops.domain.models.User;
import de.hhu.propra2.material2.mops.security.Account;
import org.keycloak.adapters.springsecurity.token.KeycloakAuthenticationToken;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;

@Service
public class DeleteService implements IDeleteService {

    private final Repository repository;
    private final MinIOService minIOService;
    private final ModelService modelService;

    /**
     * Constructor for DeleteService.
     *
     * @param repositoryArg
     * @param minIOServiceArg
     */
    public DeleteService(final Repository repositoryArg,
                         final MinIOService minIOServiceArg,
                         final ModelService modelServiceArg) {
        this.repository = repositoryArg;
        this.minIOService = minIOServiceArg;
        this.modelService = modelServiceArg;
    }

    /**
     * method to delete from repository and from data storage (MinIO) in two steps.
     *
     * @param dateiDTO
     * @throws SQLException
     */
    @Transactional
    public void dateiLoeschen(final DateiDTO dateiDTO, final GruppeDTO gruppeDTO) throws SQLException {
        repository.deleteDateiByDateiDTO(dateiDTO, gruppeDTO);
        minIOService.deleteFile(Long.toString(dateiDTO.getId()));
    }


    public void dateiLoeschenStarten(final Long dateiId, final KeycloakAuthenticationToken token,
                                     final Long gruppenId) throws SQLException, NoDeletePermissionException {

        Account account = modelService.getAccountFromKeycloak(token);
        User user = modelService.findUserByKeycloakname(account.getName());
        Gruppe gruppe = user.getGruppeById(gruppenId);

        if (!user.hasUploadPermission(gruppe)) {
            throw new NoDeletePermissionException("User has no delete permission");
        }

        Datei datei = modelService.findDateiById(dateiId);
        UserDTO userDTO = new UserDTO(datei.getUploader().getId(), null, null,
                null, null);
        GruppeDTO groupDTO = new GruppeDTO(gruppe.getId(), null,
                null, null);

        DateiDTO dateiDTO = new DateiDTO(datei.getId(), datei.getName(), userDTO, modelService.tagsToTagDTOs(datei.getTags()),
                datei.getUploaddatum(), datei.getVeroeffentlichungsdatum(), datei.getDateigroesse(),
                datei.getDateityp(), groupDTO, datei.getKategorie());
        dateiLoeschen(dateiDTO);
    }

    /**
     *
     */
    public void a() {
        System.out.println("Hello");
    }
}
