package de.hhu.propra2.material2.mops.database.entities;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.CascadeType;

import java.util.List;

@Entity
@Table(name = "tags")
@Data
public class TagDAO {
    public TagDAO() { }

    public TagDAO(final int id, final String name) {
        this.tagID = id;
        this.tagname = name;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long tagID;

    @Column(name = "tagname")
    private String tagname;

    @ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE}, mappedBy = "tags")
    private List<DateiDAO> dateien;

}
