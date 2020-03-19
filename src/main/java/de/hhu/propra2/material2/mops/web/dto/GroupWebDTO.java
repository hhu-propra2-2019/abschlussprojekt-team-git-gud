package de.hhu.propra2.material2.mops.web.dto;

import lombok.Value;

import java.util.List;
import java.util.Map;

@Value
public class GroupWebDTO {
    private String description;
    private int id;
    private List<UserWebDTO> members;
    private int parent;
    private Map<String, String> roles;
    private String title;
    private String type;
    private String visibility;
}
