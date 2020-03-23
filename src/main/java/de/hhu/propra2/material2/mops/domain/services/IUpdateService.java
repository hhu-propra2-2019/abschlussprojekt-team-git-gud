package de.hhu.propra2.material2.mops.domain.services;

import de.hhu.propra2.material2.mops.domain.models.UpdateForm;

public interface IUpdateService {
    void startUpdate(UpdateForm upForm, Long gruppenId, Long dateiId);
}
