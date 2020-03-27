package de.hhu.propra2.material2.mops.domain.services;

import de.hhu.propra2.material2.mops.database.DTOs.StatusDTO;
import de.hhu.propra2.material2.mops.database.Repository;
import org.springframework.stereotype.Service;

import java.sql.SQLException;

@Service
public class StatusService {

    private Repository repository;

    public StatusService(final Repository repo){
        repository = repo;
    }

    public long getCurrentStatus() throws SQLException {
        return repository.getStatus();
    }

    public void updateToNewStatus(long status) throws SQLException {
        StatusDTO newStatus = new StatusDTO(status);
        repository.updateStatus(newStatus);
    }
}
