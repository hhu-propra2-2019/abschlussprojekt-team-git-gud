package de.hhu.propra2.material2.mops.web.dto;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Setter
@Getter
public class GroupWebDTO implements Serializable {
    private String description;     //needed -> GroupDTO
    private String id;              //needed -> GroupDTO
    private List<UserWebDTO> members;
    private String parent;
    private Map<String, String> roles;
    private String title;           //needed -> GroupDTO
    private String type;
    private String visibility;
    private int userMaximum;
}
