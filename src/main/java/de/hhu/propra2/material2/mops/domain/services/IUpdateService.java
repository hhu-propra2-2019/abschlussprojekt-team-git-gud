package de.hhu.propra2.material2.mops.domain.services;

import de.hhu.propra2.material2.mops.Exceptions.NoUploadPermissionException;
import de.hhu.propra2.material2.mops.domain.models.UpdateForm;

import java.sql.SQLException;

public interface IUpdateService {
    void startUpdate(UpdateForm upForm, String user, Long gruppenId, Long dateiId)
            throws SQLException, NoUploadPermissionException;
}
