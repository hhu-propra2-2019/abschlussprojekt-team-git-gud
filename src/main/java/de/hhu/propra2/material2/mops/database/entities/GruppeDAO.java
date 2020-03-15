package de.hhu.propra2.material2.mops.database.entities;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.FetchType;
import javax.persistence.CascadeType;
import javax.persistence.OneToMany;

import java.util.List;

@Entity
@Table(name = "gruppe")
@Data
public class GruppeDAO {

    public GruppeDAO() { }

    public GruppeDAO(final long id, final String title, final String description) {
        this.gruppeID = id;
        this.titel = title;
        this.beschreibung = description;
    }
    /**
     * Unique ID from database.
     */
    @Id
    private long gruppeID;
    /**
     * Groups name from database.
     */
    private String titel;
    private String beschreibung;
    /**
     * List of participating users from database.
     */
    /**
     * List of participating users from database.
     */
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "gruppe")
    private List<GruppenbelegungDAO> user;
    /**
     * List of related files from database.
     */
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "gruppe")
    private List<DateiDAO> dateien;
}
