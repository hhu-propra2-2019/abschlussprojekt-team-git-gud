package de.hhu.propra2.material2.mops.database.entities;

import de.hhu.propra2.material2.mops.database.entities.conjunction_table_keys.GruppenbelegungID;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.EmbeddedId;
import javax.persistence.ManyToOne;
import javax.persistence.FetchType;
import javax.persistence.MapsId;
import javax.persistence.Column;

import java.util.Objects;

@Entity
@Table(name = "gruppenbelegung")
@Data
public class GruppenbelegungDAO {

    @EmbeddedId
    private GruppenbelegungID gruppenbelegungid;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("gruppeid")
    private GruppeDAO gruppe;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("userid")
    private UserDAO user;

    @Column(name = "uploadberechtigung")
    private boolean berechtigung;

    public GruppenbelegungDAO() { }

    public GruppenbelegungDAO(final GruppeDAO group, final UserDAO u, final boolean belegung) {
        this.gruppe = group;
        this.user = u;
        this.berechtigung = belegung;
        this.gruppenbelegungid = new GruppenbelegungID(gruppe.getGruppeID(), user.getUserID());
    }

    /**
     * Checks for equality of two object instances of Gruppenbelegung.
     * @param o
     * @return
     */
    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        GruppenbelegungDAO that = (GruppenbelegungDAO) o;
        return Objects.equals(gruppe, that.gruppe) && Objects.equals(user, that.user);
    }

    /**
     * Generates has with parameters gruppe and user.
     * @param
     * @return
     */
    @Override
    public int hashCode() {
        return Objects.hash(gruppe, user);
    }
}
