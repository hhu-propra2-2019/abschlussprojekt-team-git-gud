package de.hhu.propra2.material2.mops.database.entities;

import de.hhu.propra2.material2.mops.database.entities.conjunction_table_keys.GruppenbelegungID;
import lombok.Data;

import javax.persistence.*;
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
    private boolean berechtigt;

    public GruppenbelegungDAO() { }

    public GruppenbelegungDAO(GruppeDAO group, UserDAO u, boolean belegung) {
        this.gruppe = group;
        this.user = u;
        this.berechtigt = belegung;
        this.gruppenbelegungid = new GruppenbelegungID(gruppe.getGruppeID(), user.getUserID());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass())
            return false;

        GruppenbelegungDAO that = (GruppenbelegungDAO) o;
        return Objects.equals(gruppe, that.gruppe) && Objects.equals(user, that.user);
    }

    @Override
    public int hashCode() {
        return Objects.hash(gruppe, user);
    }
}
