package de.hhu.propra2.material2.mops;

import de.hhu.propra2.material2.mops.DTOs.DateiRepository;
import de.hhu.propra2.material2.mops.DTOs.GruppeRepository;
import de.hhu.propra2.material2.mops.DTOs.UserRepository;
import de.hhu.propra2.material2.mops.models.Datei;
import de.hhu.propra2.material2.mops.models.Gruppe;
import de.hhu.propra2.material2.mops.models.Suche;
import de.hhu.propra2.material2.mops.models.User;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class SuchService {

    private final DateiRepository dateien;
    private final GruppeRepository gruppen;
    private final UserRepository users;

    public SuchService(DateiRepository dateien, GruppeRepository gruppen, UserRepository users) {
        this.dateien = dateien;
        this.gruppen = gruppen;
        this.users = users;
    }

    public List<Datei> starteSuche(Suche suche, String keyCloackName) {
        User user = users.findByKeyCloackname(keyCloackName);
        final List<Datei> zuFiltern = new ArrayList<Datei>();
        List<Datei> result = new ArrayList<Datei>();

        if (suche.getGruppe() != null) {
            zuFiltern.addAll(suche.getGruppe().getDateien());
        } else {
            user.getAllGruppen().forEach(gruppe -> zuFiltern.addAll(gruppe.getDateien()));
        }
        result.addAll(zuFiltern);
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
            result = datumsSuche(suche.getVonDatum(), suche.getBisDatum(), zuFiltern);
        }
        return result;
    }

    /**
     * Suchmethoden noch zu implementieren
     */
    public List<Datei> tagSuche(String[] tags, List<Datei> zuFiltern) {
        return zuFiltern.stream()
                .filter(datei -> datei.hatTags(tags))
                .collect(Collectors.toList());
    }


    public List<Datei> datumsSuche(String vonDatum, String bisDatum, List<Datei> zuFiltern) {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        LocalDate von = LocalDate.parse(vonDatum, dtf);
        LocalDate bis = LocalDate.parse(bisDatum, dtf);

        return zuFiltern.stream().filter(datei -> datumInZeitraum(von, bis, datei.getUploaddatum())).collect(Collectors.toList());

    }

    private boolean datumInZeitraum(LocalDate von, LocalDate bis, Date zuPruefen) {
        LocalDate pruefen = zuPruefen.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        return von.compareTo(pruefen) <= 0 && bis.compareTo(pruefen) >= 0;
    }

    public List<Datei> typSuche(String[] typen, List<Datei> zuFiltern) {
        List<Datei> result = new ArrayList<>();
        for (String typ : typen) {
            result.addAll(zuFiltern.stream()
                    .filter(datei -> datei.getDateityp().equals(typ))
                    .collect(Collectors.toList()));
        }
        return result;
    }

    public List<Datei> uploaderSuche(String[] uploader, List<Datei> zuFiltern) {
        List<Datei> result = new ArrayList<>();
        for (String upload : uploader) {
            result.addAll(zuFiltern.stream()
                    .filter(datei -> datei.getUploader().equals(upload))
                    .collect(Collectors.toList()));
        }
        return result;
    }

    /**

     public List<Datei> sortieren(String sortierart, List<Datei> zuFiltern){

     }
     **/
}
