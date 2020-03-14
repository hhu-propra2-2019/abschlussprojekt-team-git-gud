package de.hhu.propra2.material2.mops.domain.services;

import de.hhu.propra2.material2.mops.database.DateiRepository;
import de.hhu.propra2.material2.mops.database.GruppeRepository;
import de.hhu.propra2.material2.mops.database.UserRepository;
import de.hhu.propra2.material2.mops.database.entities.Datei;
import de.hhu.propra2.material2.mops.database.entities.User;
import de.hhu.propra2.material2.mops.domain.services.suchComparators.DateiDateiTypComparator;
import de.hhu.propra2.material2.mops.domain.services.suchComparators.DateiDatumComparator;
import de.hhu.propra2.material2.mops.domain.services.suchComparators.DateiNamenComparator;
import de.hhu.propra2.material2.mops.domain.services.suchComparators.DateiUploaderComparator;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SuchService {

    private final DateiRepository dateiRepository;
    private final GruppeRepository gruppeRepository;
    private final UserRepository userRepository;
    private final ModelService modelService;

    public SuchService(final DateiRepository dateienArg,
                       final GruppeRepository gruppenArg,
                       final UserRepository usersArg,
                       final ModelService modelServiceArg) {
        this.dateiRepository = dateienArg;
        this.gruppeRepository = gruppenArg;
        this.userRepository = usersArg;
        this.modelService = modelServiceArg;
    }

    /**
     * @param suche
     * @param keyCloackName
     * @return
     */
    public List<Datei> starteSuche(final Suche suche,
                                   final String keyCloackName) {
        User user = modelService.loadUser(userRepository.findByKeycloakname(keyCloackName));

        final List<Datei> zuFiltern = new ArrayList<>();
        List<Datei> result;

        if (suche.getGruppenId() != null) {
            zuFiltern.addAll(modelService.getAlleDateienByGruppeId(suche.getGruppenId()));
        } else {
            user.getGruppen()
                    .forEach(gruppe -> zuFiltern.addAll(gruppe.getDateien()));
        }
        result = zuFiltern;
        if (suche.getTags() != null) {
            result = tagSuche(suche.getTags(), result);
        }
        if (suche.getDateiTyp() != null) {
            result = typSuche(suche.getDateiTyp(), result);
        }
        if (suche.getUploader() != null) {
            result = uploaderSuche(suche.getUploader(), result);
        }
        if (!suche.getBisDatum().isEmpty() || !suche.getVonDatum().isEmpty()) {

            result = datumsSuche(suche.getVonDatum(),
                    suche.getBisDatum(),
                    result);
        }
        if (!suche.getDateiName().trim().isEmpty()) {
            result = dateiNamenSuche(suche.getDateiName(), result);
        }
        if (suche.getReihenfolge() != null) {
            result = sortieren(suche.getSortierung(),
                    suche.getReihenfolge(),
                    result);
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


    private List<Datei> datumsSuche(final String vonDatumArg,
                                    final String bisDatumArg,
                                    final List<Datei> zuFiltern) {
        String vonDatum;
        String bisDatum;
        vonDatum = vonDatumArg.isEmpty() ? "2000-01-01" : vonDatumArg;
        bisDatum = bisDatumArg.isEmpty() ? LocalDate.MAX.toString() : bisDatumArg;
        LocalDate von = LocalDate.parse(vonDatum);
        LocalDate bis = LocalDate.parse(bisDatum);

        return zuFiltern.stream()
                .filter(datei -> datumInZeitraum(von,
                        bis,
                        datei.getUploaddatum()))
                .collect(Collectors.toList());

    }

    private List<Datei> dateiNamenSuche(final String dateiName,
                                        final List<Datei> zuFiltern) {
        return zuFiltern.stream().filter(datei -> datei.getName()
                .toLowerCase()
                .contains(dateiName.toLowerCase()))
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
                            .equalsIgnoreCase(upload))
                    .collect(Collectors.toList()));
        }
        return result;
    }

    private List<Datei> sortieren(final String sortierStyle, final String reihenfolge, final List<Datei> zuSortieren) {
        /**
         * Name
         * Datum
         * Dateityp
         * Uploader
         * Kategorie
         * aufsteigend / absteigend
         */
        List<Datei> sort = zuSortieren;
        if ("name".equals(sortierStyle)) {
            sort.sort(new DateiNamenComparator());
        }
        if ("Dateityp".equals(sortierStyle)) {
            sort.sort(new DateiDateiTypComparator());
        }
        if ("Uploader".equals(sortierStyle)) {
            sort.sort(new DateiUploaderComparator());
        }
        if ("Datum".equals(sortierStyle)) {
            sort.sort(new DateiDatumComparator());
        }
        if (reihenfolge != null) {
            if ("absteigend".equals(reihenfolge)) {
                Collections.reverse(sort);
            }
        }
        return sort;
    }
}
