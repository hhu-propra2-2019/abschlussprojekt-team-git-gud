package de.hhu.propra2.material2.mops.database.entities.conjunction_table_keys;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
@Data
public class GruppenbelegungID implements Serializable {

    @Column(name = "gruppeid")
    private long gruppeid;

    @Column(name = "userid")
    private long userid;

    public GruppenbelegungID() { }

    public GruppenbelegungID(long gid, long uid) {
        this.gruppeid = gid;
        this.userid = uid;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o){
            return true;
        }
        if (o == null || getClass() != o.getClass()){
            return false;
        }

        GruppenbelegungID that = (GruppenbelegungID) o;
        return Objects.equals(gruppeid, that.gruppeid) && Objects.equals(userid, that.userid);
    }

    @Override
    public int hashCode() {
        return Objects.hash(gruppeid, userid);
    }

}
