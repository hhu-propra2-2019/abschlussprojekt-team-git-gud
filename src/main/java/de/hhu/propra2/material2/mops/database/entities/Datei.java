package de.hhu.propra2.material2.mops.database.entities;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@Data
public final class Datei {
    public Datei() { }

    public Datei(final long id, final String fileName, final String fileDir, final String fileSubmitter, final LocalDate uploadDate, final LocalDate releaseDate, final long fileSize, final String dataTyp, final Gruppe group) {
        this.dateiID = id;
        this.name = fileName;
        this.pfad = fileDir;
        this.uploader = fileSubmitter;
        this.uploaddatum = uploadDate;
        this.veroeffentlichungsdatum = releaseDate;
        this.dateigroesse = fileSize;
        this.dateityp = dataTyp;
        this.gruppe = group;
    }
    /**
     * Unique ID from database.
     */
    @Id
    private long dateiID;
    /**
     * Name of file.
     */
    private String name;
    /**
     * Path of file.
     */
    private String pfad;
    /**
     * User that uploaded the file.
     */
    private String uploader;
    /**
     * Upload date.
     */
    private LocalDate uploaddatum;
    /**
     * Date for when the file
     * will be visible to non-uploaders
     * of its group.
     */
    private LocalDate veroeffentlichungsdatum;
    /**
     * File size.
     */
    private long dateigroesse;
    /**
     * File type.
     */
    private String dateityp;
    /**
     * All assigned tags.
     */
    @ManyToMany
    @JoinTable(name = "Tagnutzung", joinColumns = {@JoinColumn(name = "dateiID")}, inverseJoinColumns = {@JoinColumn(name = "tagID")})
    private Set<Tag> tags = new HashSet<>();
    /**
     * Assigned group.
     */
    @ManyToOne
    private Gruppe gruppe;

}
