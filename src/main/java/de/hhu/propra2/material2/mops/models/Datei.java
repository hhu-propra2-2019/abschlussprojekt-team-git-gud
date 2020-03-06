package de.hhu.propra2.material2.mops.models;


import lombok.Value;

import java.util.Date;
import java.util.List;

@Value
public class Datei {
    private long id;
    private String name;
    private String pfad;
    final User uploader;
    final List<Tag> tags;
    //final Gruppe gruppe;
    final Date uploaddatum;
    final Date veroeffentlichungsdatum;
    final double dateigroesse;
    final String dateityp;

}
