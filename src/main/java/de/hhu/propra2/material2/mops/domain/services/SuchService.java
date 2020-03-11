package de.hhu.propra2.material2.mops.domain.services;

import de.hhu.propra2.material2.mops.Database.Repository;
import de.hhu.propra2.material2.mops.domain.models.Datei;
import de.hhu.propra2.material2.mops.domain.models.User;
import de.hhu.propra2.material2.mops.domain.models.Suche;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class SuchService {

    private final ModelService modelService;

    public SuchService(final ModelService modelServiceArg) {
        this.modelService = modelServiceArg;
    }

    /**
     * @param suche
     * @param keyCloackName
     * @return
     */
    public List<Datei> starteSuche(final Suche suche,
                                   final String keyCloackName) {
        User user;
        try {
            user = modelService.load(Repository.findUserByKeycloakname(keyCloackName));
        } catch (SQLException e) {
            log.error("Unknown SQLException occured.");
            return new ArrayList<>();
        }

        final List<Datei> zuFiltern = new ArrayList<>();
        List<Datei> result = new ArrayList<>();

        if (suche.getGruppe() != null) {
            zuFiltern.addAll(suche.getGruppe().getDateien());
        } else {
            user.getAllGruppen()
                    .forEach(gruppe -> zuFiltern.addAll(gruppe.getDateien()));
        }
        result = zuFiltern;
        if (suche.getTags() != null) {
            result = tagSuche(suche.getTags(), zuFiltern);
        }
        if (suche.getDateiTyp() != null) {
            result = typSuche(suche.getDateiTyp(), result);
        }
        if (suche.getUploader() != null) {
            result = uploaderSuche(suche.getUploader(), result);
        }
        if (suche.getBisDatum() != null) {
            result = datumsSuche(suche.getVonDatum(),
                    suche.getBisDatum(),
                    zuFiltern);
        }
        return result;
    }

    /**
     * Suchmethoden noch zu implementieren
     */
    private List<Datei> tagSuche(final String[] tags,
                                 final List<Datei> zuFiltern) {
        return zuFiltern.stream()
                .filter(datei -> datei.hatTags(tags))
                .collect(Collectors.toList());
    }


    private List<Datei> datumsSuche(final String vonDatum,
                                    final String bisDatum,
                                    final List<Datei> zuFiltern) {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        LocalDate von = LocalDate.parse(vonDatum, dtf);
        LocalDate bis = LocalDate.parse(bisDatum, dtf);

        return zuFiltern.stream()
                .filter(datei -> datumInZeitraum(von,
                        bis,
                        datei.getVeroeffentlichungsdatum()))
                .collect(Collectors.toList());

    }

    private boolean datumInZeitraum(final LocalDate von,
                                    final LocalDate bis,
                                    final LocalDate zuPruefen) {
        return von.compareTo(zuPruefen) <= 0 && bis.compareTo(zuPruefen) >= 0;
    }

    private List<Datei> typSuche(final String[] typen,
                                final List<Datei> zuFiltern) {
        List<Datei> result = new ArrayList<>();
        for (String typ : typen) {
            result.addAll(zuFiltern.stream()
                    .filter(datei -> datei.getDateityp().equals(typ))
                    .collect(Collectors.toList()));
        }
        return result;
    }

    private List<Datei> uploaderSuche(final String[] uploader,
                                      final List<Datei> zuFiltern) {
        List<Datei> result = new ArrayList<>();
        for (String upload : uploader) {
            result.addAll(zuFiltern.stream()
                    .filter(datei -> datei.getUploader()
                            .getNachname()
                            .equalsIgnoreCase(upload))
                    .collect(Collectors.toList()));
        }
        return result;
    }
}
