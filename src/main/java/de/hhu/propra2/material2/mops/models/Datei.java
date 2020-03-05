package de.hhu.propra2.material2.mops.models;


import lombok.Value;

import java.util.Date;
import java.util.List;

@Value
public class Datei {
    final long id;
    final String name;
    final String pfad;
    final User uploader;
    final List<Tag> tag;
    //final Gruppe gruppe;
    final Date uploaddatum;
    final Date veroeffentlichungsdatum;
    final double dateigroesse;
    final String dateityp;
}
