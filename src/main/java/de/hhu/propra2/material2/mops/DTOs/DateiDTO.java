package de.hhu.propra2.material2.mops.DTOs;

import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class DateiDTO {
    final long id;
    final String name;
    final String pfad;
    final UserDTO uploader;
    final List<TagDTO> tagDTOS;
    final GruppeDTO gruppe;
    final Date uploaddatum;
    final Date veroeffentlichungsdatum;
    final double dateigroesse;
    final String dateityp;


    public DateiDTO(int id, String name, String pfad, String uploader, List<TagDTO> tagDTOS, Date uploaddatum, Date veroeffentlichungsdatum, double dateigroesse, String dateityp, GruppeDTO gruppe) {
        this.id = id;
        this.name = name;
        this.pfad = pfad;
        this.uploader = uploader;
        this.tagDTOS = tagDTOS;
        this.gruppe = gruppe;
        this.uploaddatum = uploaddatum;
        this.veroeffentlichungsdatum = veroeffentlichungsdatum;
        this.dateigroesse = dateigroesse;
        this.dateityp = dateityp;
    }
}
