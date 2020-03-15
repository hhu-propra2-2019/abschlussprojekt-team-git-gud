package de.hhu.propra2.material2.mops.database.entities;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.JoinTable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "datei")
@Data
public final class DateiDAO {
    public DateiDAO() { }

    public DateiDAO(final long id, final String fileName, final String fileDir,
                    final UserDAO fileSubmitter, final LocalDate uploadDate, final LocalDate releaseDate,
                    final long fileSize, final String dataTyp, final String category, final GruppeDAO group) {
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

    @OneToOne
    private UserDAO uploader;

    private LocalDate uploaddatum;

    private LocalDate veroeffentlichungsdatum;

    private long dateigroesse;

    private String dateityp;

    @ManyToOne
    private GruppeDAO gruppe;

    private String kategorie;

    @ManyToMany
    @JoinTable(name = "tagnutzung",
               joinColumns = {@JoinColumn(name = "dateiid")}, inverseJoinColumns = {@JoinColumn(name = "tagid")})
    private List<TagDAO> tagDAOS;





}
