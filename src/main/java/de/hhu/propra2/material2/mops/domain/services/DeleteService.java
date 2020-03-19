package de.hhu.propra2.material2.mops.domain.services;

import de.hhu.propra2.material2.mops.Database.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;

@Service
public class DeleteService {

    private final Repository repository;
    private final FileDeleteService fileDeleteService;
    private final ModelService modelService;

    public DeleteService(final Repository repositoryArgs, final FileDeleteService fileDeleteServiceArgs, final ModelService modelServiceArgs) {
        this.repository = repositoryArgs;
        this.fileDeleteService = fileDeleteServiceArgs;
        this.modelService = modelServiceArgs;
    }

    @Transactional
    public void dateiLoeschen(long dateiID) throws SQLException {
        //repository delete
        repository.deleteDateiByDateiId(dateiID);

        //minio delete
        fileDeleteService.deleteFile(dateiID);
    }

    public void deleteGroup(long gruppeID) {
        //TODO
        //repository.deleteGroupByGroupDTO();
    }

    public void deleteUser(long userID) {
        //TODO
        //repository.deleteUserByUserDTO();
    }



}
