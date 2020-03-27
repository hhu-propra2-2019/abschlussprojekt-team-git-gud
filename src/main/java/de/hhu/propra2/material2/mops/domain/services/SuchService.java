package de.hhu.propra2.material2.mops.domain.services;

import de.hhu.propra2.material2.mops.domain.models.Datei;
import de.hhu.propra2.material2.mops.domain.models.Suche;
import de.hhu.propra2.material2.mops.domain.services.suchComparators.DateiDateiTypComparator;
import de.hhu.propra2.material2.mops.domain.services.suchComparators.DateiDatumComparator;
import de.hhu.propra2.material2.mops.domain.services.suchComparators.DateiNamenComparator;
import de.hhu.propra2.material2.mops.domain.services.suchComparators.DateiUploaderComparator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class SuchService {

    /**
     * @param suche
     * @param zuFiltern
     * @return
     */
    public List<Datei> starteSuche(final Suche suche,
                                   final List<Datei> zuFiltern) {
        List<Datei> result = zuFiltern;

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
        result = filterVeroeffentlichung(result);
        if (suche.getSortierKriterium() != null) {
            result = sortieren(suche.getSortierKriterium(),
                    suche.getReihenfolge(),
                    result);
        }
        if (result.isEmpty()) {
            return null;
        }
        return result;
    }

    private List<Datei> filterVeroeffentlichung(final List<Datei> resultArg) {
        LocalDate today = LocalDate.now();
        List<Datei> result = resultArg.stream()
                .filter(datei -> datei.getVeroeffentlichungsdatum().compareTo(today) <= 0)
                .collect(Collectors.toList());
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
                        datei.getVeroeffentlichungsdatum()))
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
                            .getNachname()
                            .equalsIgnoreCase(upload))
                    .collect(Collectors.toList()));
        }
        return result;
    }

    private List<Datei> sortieren(final String sortierStyle, final String reihenfolge, final List<Datei> zuSortieren) {
        /*
          Name
          Datum
          Dateityp
          Uploader
          Kategorie
          aufsteigend / absteigend
         */
        List<Datei> sort = zuSortieren;
        if ("Name".equals(sortierStyle)) {
            sort.sort(new DateiNamenComparator());
        } else if ("Dateityp".equals(sortierStyle)) {
            zuSortieren.sort(new DateiDateiTypComparator());
        } else if ("Uploader".equals(sortierStyle)) {
            zuSortieren.sort(new DateiUploaderComparator());
        } else if ("Datum".equals(sortierStyle)) {
            zuSortieren.sort(new DateiDatumComparator());
        }
        if (reihenfolge != null) {
            if ("absteigend".equals(reihenfolge)) {
                Collections.reverse(zuSortieren);
            }
        }
        return zuSortieren;
    }
}
