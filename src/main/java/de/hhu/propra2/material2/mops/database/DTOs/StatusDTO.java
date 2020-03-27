package de.hhu.propra2.material2.mops.database.DTOs;

import lombok.Getter;

public class StatusDTO {
    /**
     * Status from database.
     */
    @Getter
    private final long status;

    public StatusDTO(final long state){
        this.status = state;
    }
}
