package de.hhu.propra2.material2.mops.web.dto;

import lombok.Value;

import java.util.Map;

@Value
public class GroupWebDTO {
    private String description;
    private int id;
    private UserWebDTO[] members;
    private int parent;
    private Map<Integer, String> roles;
    private String title;
    private String type;
    private String visibility;
}
