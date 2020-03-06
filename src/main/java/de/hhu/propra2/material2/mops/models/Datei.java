package de.hhu.propra2.material2.mops.models;

import lombok.Getter;

import java.util.Date;
import java.util.List;

public class Datei {
    /**
     * Unique ID from database.
     */
    @Getter
    private long id;
    /**
     * Name of file.
     */
    @Getter
    private String name;
    /**
     * Path of file.
     */
    @Getter
    private final String pfad;
    /**
     * User that uploaded the file.
     */
    @Getter
    private final User uploader;
    /**
     * All assigned tags.
     */
    @Getter
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
    @Getter
    private final double dateigroesse;
    /**
     * File type.
     */
    @Getter
    private final String dateityp;

    public Datei(
            final long idArgs,
            final String nameArgs,
            final String pfadArgs,
            final User uploaderArgs,
            final List<Tag> tagsArgs,
            final Date uploaddatumArgs,
            final Date veroeffentlichungsdatumArgs,
            final double dateigroesseArgs,
            final String dateitypArgs) {
        this.id = idArgs;
        this.name = nameArgs;
        this.pfad = pfadArgs;
        this.uploader = uploaderArgs;
        this.tags = tagsArgs;
        this.uploaddatum = (Date) uploaddatumArgs.clone();
        this.veroeffentlichungsdatum =
                (Date) veroeffentlichungsdatumArgs.clone();
        this.dateigroesse = dateigroesseArgs;
        this.dateityp = dateitypArgs;
    }

    public final Date getUploaddatum() {
        return (Date) uploaddatum.clone();
    }

    public final Date getVeroeffentlichungsdatum() {
        return (Date) veroeffentlichungsdatum.clone();
    }

}
