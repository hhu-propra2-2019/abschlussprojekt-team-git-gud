package de.hhu.propra2.material2.mops.models;


import lombok.Value;

import java.util.Date;
import java.util.List;

@Value
public class Datei {
    /**
     * Unique ID from database.
     */
    private long id;
    /**
     * Name of file.
     */
    private String name;
    /**
     * Path of file.
     */
    private final String pfad;
    /**
     *  User that uploaded the file.
     */
    private final User uploader;
    /**
     * All assigned tags.
     */
    private final List<Tag> tags;
    //final Gruppe gruppe;
    /**
     * Upload date.
     */
    private final Date uploaddatum;
    /**
     * Date for when the file
     * will be visible to non-uploaders
     * of its group.
     */
    private final Date veroeffentlichungsdatum;
    /**
     * File size.
     */
    private final double dateigroesse;
    /**
     * File type.
     */
    private final String dateityp;

}
