package de.hhu.propra2.material2.mops.domain.services;

import de.hhu.propra2.material2.mops.domain.models.Datei;
import de.hhu.propra2.material2.mops.domain.models.Tag;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class UpdateService {

    private final ModelService modelService;

    public UpdateService(final ModelService modelServiceArg) {
        this.modelService = modelServiceArg;
    }

    /**
     * @param dateiId                 The id of the file which should be updated
     * @param veroeffentlichungsdatum The new date which controls the availability of the file.
     *                                If null the file is direct available after upload.
     * @param tags                    The new tags for the file
     */
    @Transactional
    public void dateiUpdate(final Long dateiId, final String gruppenId, final LocalDate veroeffentlichungsdatum,
                             final List<Tag> tags) throws SQLException {
        Datei datei = modelService.findDateiById(dateiId);
        Datei changedDatei = new Datei(dateiId, datei.getName(), datei.getUploader(),
                tags == null ? new ArrayList<>() : tags,
                datei.getUploaddatum(), veroeffentlichungsdatum, datei.getDateigroesse(),
                datei.getDateityp(), datei.getKategorie());

        modelService.saveDatei(changedDatei, gruppenId);
    }
}
