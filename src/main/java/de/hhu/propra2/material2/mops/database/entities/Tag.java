package de.hhu.propra2.material2.mops.database.entities;
import lombok.Data;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "Tags")
@Data
public class Tag {
    public Tag() { }

    public Tag(final int id, final String name) {
        this.tagID = id;
        this.tagname = name;
    }
    /**
     * Unique ID from database.
     */
    @Id
    private long tagID;
    /**
     * The tags text that will be shown
     * and can be searched for.
     */
    @Column(name = "tag_name")
    private String tagname;
    /**
     * Related files to this tag.
     */
    @ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE}, mappedBy = "tags")
    private Set<Datei> dateien;

}
