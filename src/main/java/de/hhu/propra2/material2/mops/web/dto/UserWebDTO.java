package de.hhu.propra2.material2.mops.web.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.Value;

import java.io.Serializable;

@Setter
@Getter
public class UserWebDTO implements Serializable {
    private String email;
    private String familyname;
    private String givenname;
    private String id;
}
