package de.hhu.propra2.material2.mops.domain.services;

import de.hhu.propra2.material2.mops.database.DTOs.StatusDTO;
import de.hhu.propra2.material2.mops.database.Repository;
import org.springframework.stereotype.Service;

import java.sql.SQLException;

@Service
public class StatusService {

    private Repository repository;

    public StatusService(final Repository repo) {
        repository = repo;
    }

    /**
     * @return
     * @throws SQLException
     */
    public long getCurrentStatus() throws SQLException {
        return repository.getStatus();
    }

    /**
     * @param status
     * @throws SQLException
     */
    public void updateToNewStatus(final long status) throws SQLException {
        StatusDTO newStatus = new StatusDTO(status);
        repository.updateStatus(newStatus);
    }
}
