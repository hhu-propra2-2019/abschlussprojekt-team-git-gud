package de.hhu.propra2.material2.mops.web.dto;

import lombok.AllArgsConstructor;
import lombok.Value;

import java.util.List;

@Value
public class UpdatedGroupRequestMapper {
    private List<GroupWebDTO> groupList;
    private int status;
}
