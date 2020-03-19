package de.hhu.propra2.material2.mops.web.dto;

import java.util.Map;

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
