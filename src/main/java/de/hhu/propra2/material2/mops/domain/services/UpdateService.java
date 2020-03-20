package de.hhu.propra2.material2.mops.domain.services;

import de.hhu.propra2.material2.mops.Database.DTOs.DateiDTO;
import de.hhu.propra2.material2.mops.Database.Repository;
import de.hhu.propra2.material2.mops.domain.models.Datei;
import de.hhu.propra2.material2.mops.domain.models.Tag;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

@Service
public class UpdateService {

    private final Repository repository;
    private final ModelService modelService;

    public UpdateService(final Repository repositoryArg, final ModelService modelServiceArg) {
        this.repository = repositoryArg;
        this.modelService = modelServiceArg;
    }

    /**
     * @param dateiId                 The id of the file which should be updated
     * @param veroeffentlichungsdatum The new date which controls the availability of the file. If null the file is direct
     *                                available after upload.
     * @param tags                    The new tags for the file
     * @return A Datei object which represents the saved File
     */
    @Transactional
    public Datei dateiUpdate(final Long dateiId, final Long gruppenId, final LocalDate veroeffentlichungsdatum,
                             final List<Tag> tags) throws SQLException {
        DateiDTO dateiDTO = repository.findDateiById(dateiId);
        Datei datei = modelService.loadDatei(dateiDTO);
        Datei changedDatei = new Datei(dateiId, datei.getName(), datei.getUploader(), tags,
                datei.getUploaddatum(), veroeffentlichungsdatum, datei.getDateigroesse(),
                datei.getDateityp(), datei.getKategorie());

        modelService.saveDatei(datei, gruppenId);

        return changedDatei;
    }
}
