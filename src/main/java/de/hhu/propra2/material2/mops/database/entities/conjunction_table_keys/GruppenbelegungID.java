package de.hhu.propra2.material2.mops.database.entities.conjunction_table_keys;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
@Data
public class GruppenbelegungID implements Serializable {

    @Column(name = "gruppe_gruppeid")
    private long gruppeid;

    @Column(name = "user_userid")
    private long userid;

    public GruppenbelegungID() { }

    public GruppenbelegungID(final long gid, final long uid) {
        this.gruppeid = gid;
        this.userid = uid;
    }

    /**
     * Checks for equality of two object instances of GruppenbelegungID.
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

        GruppenbelegungID that = (GruppenbelegungID) o;
        return Objects.equals(gruppeid, that.gruppeid) && Objects.equals(userid, that.userid);
    }

    /**
     * Generates Hash code.
     * @param
     * @return
     */
    @Override
    public int hashCode() {
        return Objects.hash(gruppeid, userid);
    }

}
