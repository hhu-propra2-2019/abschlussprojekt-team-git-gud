package de.hhu.propra2.material2.mops;

import de.hhu.propra2.material2.mops.DTOs.DateiRepository;
import de.hhu.propra2.material2.mops.DTOs.GruppeRepository;
import de.hhu.propra2.material2.mops.DTOs.UserRepository;
import de.hhu.propra2.material2.mops.models.Datei;
import de.hhu.propra2.material2.mops.models.Gruppe;
import de.hhu.propra2.material2.mops.models.Suche;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SuchService {

    private final DateiRepository dateien;
    private final GruppeRepository gruppen;
    private final UserRepository users;

    public SuchService(DateiRepository dateien,GruppeRepository gruppen,UserRepository users){
        this.dateien = dateien;
        this.gruppen = gruppen;
        this.users = users;
    }

    public List<Datei> starteSuche(Suche suche, Gruppe gruppe){
        List<Datei> dateienUngefiltert = gruppe.getDateien();

        if(suche.getTags() != null){
            dateienUngefiltert = tagSuche(suche.getTags(), dateienUngefiltert);
        }

        return dateienUngefiltert;
    }

    /**
     *  Suchmethoden noch zu implementieren
     */
    public List<Datei> tagSuche(String[] tags,List<Datei> zuFiltern){
        return zuFiltern.stream().filter(datei -> datei.hatTags(tags)).collect(Collectors.toList());
    }
    /**
    public List<Datei> datumsSuche(String vonDatum,String bisDatum, List<Datei> zuFiltern){
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        LocalDate von = LocalDate.parse(vonDatum,dtf);

    }
    **/
    public List<Datei> typSuche(String[] typen, List<Datei> zuFiltern){
        for(String typ:typen) {
            zuFiltern.stream().filter(datei -> datei.getDateityp().equals(typ)).collect(Collectors.toList());
        }
        return zuFiltern;
    }
    public List<Datei> uploaderSuche(String[] uploader, List<Datei> zuFiltern){
        for(String upload:uploader){
            zuFiltern.stream().filter(datei -> datei.getUploader().getNachname().equals(uploader)).collect(Collectors.toList());
        }
        return zuFiltern;
    }

    /**

    public List<Datei> sortieren(String sortierart, List<Datei> zuFiltern){

    }
     **/
}
