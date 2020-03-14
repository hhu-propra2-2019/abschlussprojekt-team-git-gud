package de.hhu.propra2.material2.mops.database.entities;
import lombok.Data;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "tags")
@Data
public class Tag {
    public Tag() { }

    public Tag(final int id, final String name) {
        this.tagID = id;
        this.tagname = name;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long tagID;

    @Column(name = "tagname")
    private String tagname;

    @ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE}, mappedBy = "tags")
    private List<Datei> dateien;

}
