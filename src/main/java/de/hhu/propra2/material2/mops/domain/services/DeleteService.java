package de.hhu.propra2.material2.mops.domain.services;

import de.hhu.propra2.material2.mops.Exceptions.NoDeletePermissionException;
import de.hhu.propra2.material2.mops.Exceptions.ObjectNotInMinioException;
import de.hhu.propra2.material2.mops.database.DTOs.DateiDTO;
import de.hhu.propra2.material2.mops.database.Repository;
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
    public void dateiLoeschen(final DateiDTO dateiDTO) throws SQLException, ObjectNotInMinioException {
        repository.deleteDateiByDateiDTO(dateiDTO);
        if (!minIOService.deleteFile(Long.toString(dateiDTO.getId()))) {
            throw new ObjectNotInMinioException("The object with the id:" + dateiDTO.getId() + "doesnt exist in MinIO");
        }
    }

    /**
     * @param dateiId
     * @param token
     * @throws SQLException
     * @throws NoDeletePermissionException
     */
    @Transactional
    public void dateiLoeschenStarten(final Long dateiId, final KeycloakAuthenticationToken token)
            throws SQLException, NoDeletePermissionException, ObjectNotInMinioException {

        Account account = modelService.getAccountFromKeycloak(token);
        DateiDTO dateiDTO = repository.findDateiById(dateiId);
        User user = modelService.findUserByKeycloakname(account.getName());
        String gruppenId = dateiDTO.getGruppe().getId();
        Gruppe gruppe = user.getGruppeById(gruppenId);

        if (!user.hasUploadPermission(gruppe)) {
            throw new NoDeletePermissionException("User has no delete permission");
        }

        dateiLoeschen(dateiDTO);
    }
}
