package de.hhu.propra2.material2.mops.database.entities;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
public class Gruppe {

    public Gruppe() { }

    public Gruppe(final long id, final String title, final String description) {
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
    /**
     * List of participating users from database.
     */
    private String beschreibung;
    /**
     * List of participating users from database.
     */
    @ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE}, mappedBy = "gruppen")
    private List<User> users;
    /**
     * List of related files from database.
     */
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "gruppe")
    private List<Datei> dateien;
}
