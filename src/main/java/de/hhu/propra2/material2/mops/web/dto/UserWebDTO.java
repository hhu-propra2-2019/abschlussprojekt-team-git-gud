package de.hhu.propra2.material2.mops.web.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Value;

@Value
public class UserWebDTO {
    public String email;
    public String familyname;
    public String givenname;
    @JsonProperty("user_id")
    public String userId;
}
