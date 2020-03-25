package de.hhu.propra2.material2.mops.domain.services;

import de.hhu.propra2.material2.mops.database.DTOs.DateiDTO;
import de.hhu.propra2.material2.mops.database.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;

@Service
public class DeleteService {

    private final Repository repository;
    private final MinIOService minIOService;

    /**
     * Constructor for DeleteService.
     * @param repositoryArg
     * @param minIOServiceArg
     */
    public DeleteService(final Repository repositoryArg,
                         final MinIOService minIOServiceArg) {
        this.repository = repositoryArg;
        this.minIOService = minIOServiceArg;
    }

    /**
     * method to delete from repository and from data storage (MinIO) in two steps.
     * @param dateiDTO
     * @throws SQLException
     */
    @Transactional
    public void dateiLoeschen(final DateiDTO dateiDTO) throws SQLException {
        repository.deleteDateiByDateiDTO(dateiDTO);
        minIOService.deleteFile(Long.toString(dateiDTO.getId()));
    }
}
