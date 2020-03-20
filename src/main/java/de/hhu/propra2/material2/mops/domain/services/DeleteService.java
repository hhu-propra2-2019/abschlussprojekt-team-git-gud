package de.hhu.propra2.material2.mops.domain.services;

import de.hhu.propra2.material2.mops.Database.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;

@Service
public class DeleteService {

    private final Repository repository;
    private final MinIOService minIOService;
    private final ModelService modelService;

    public DeleteService(final Repository repositoryArg, final MinIOService minIOServiceArg, final ModelService modelServiceArg) {
        this.repository = repositoryArg;
        this.minIOService = minIOServiceArg;
        this.modelService = modelServiceArg;
    }

    @Transactional
    public void dateiLoeschen(long dateiID) throws SQLException {
        repository.deleteDateiByDateiId(dateiID);
        minIOService.deleteFile(dateiID);
    }

    public void deleteUser(long userID) {
        //TODO
        //beim synchronisieren mit der Gruppenbildung
        //repository.deleteUserByUserDTO();
    }



}
