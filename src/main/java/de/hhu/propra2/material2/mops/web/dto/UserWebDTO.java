package de.hhu.propra2.material2.mops.web.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Value;


@Value
public class UserWebDTO {
    private String email;
    private String familyname;
    private String givenname;
    @JsonProperty("user_id")
    private String userId;
}
