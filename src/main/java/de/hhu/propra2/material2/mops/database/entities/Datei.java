package de.hhu.propra2.material2.mops.database.entities;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "datei")
@Data
public final class Datei {
    public Datei() { }

    public Datei(final long id, final String fileName, final String fileDir, final User fileSubmitter, final LocalDate uploadDate, final LocalDate releaseDate, final long fileSize, final String dataTyp, final String category, final Gruppe group) {
        this.dateiID = id;
        this.name = fileName;
        this.pfad = fileDir;
        this.uploader = fileSubmitter;
        this.uploaddatum = uploadDate;
        this.veroeffentlichungsdatum = releaseDate;
        this.dateigroesse = fileSize;
        this.dateityp = dataTyp;
        this.kategorie = category;
        this.gruppe = group;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long dateiID;
    private String name;
    private String pfad;
    private LocalDate uploaddatum;
    private LocalDate veroeffentlichungsdatum;
    private long dateigroesse;
    private String dateityp;
    private String kategorie;

    @ManyToMany
    @JoinTable(name = "tagnutzung", joinColumns = {@JoinColumn(name = "dateiid")}, inverseJoinColumns = {@JoinColumn(name = "tagid")})
    private List<Tag> tags;

    @ManyToOne
    private Gruppe gruppe;

    @OneToOne
    private User uploader;

}
