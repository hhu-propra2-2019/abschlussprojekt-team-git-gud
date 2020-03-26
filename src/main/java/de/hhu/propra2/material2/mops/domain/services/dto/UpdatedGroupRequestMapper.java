package de.hhu.propra2.material2.mops.domain.services.dto;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;


@Setter
@Getter
public class UpdatedGroupRequestMapper implements Serializable {
    private int status;
    private List<GroupWebDTO> groupList;

}
