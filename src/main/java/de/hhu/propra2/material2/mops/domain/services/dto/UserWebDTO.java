package de.hhu.propra2.material2.mops.domain.services.dto;

import lombok.Getter;
import lombok.Setter;
import java.io.Serializable;

@Setter
@Getter
public class UserWebDTO implements Serializable {
    private String email;
    private String familyname;
    private String givenname;
    private String id;
}
