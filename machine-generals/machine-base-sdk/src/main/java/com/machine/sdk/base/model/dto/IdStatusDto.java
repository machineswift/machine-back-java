package com.machine.sdk.base.model.dto;

import com.machine.sdk.base.envm.StatusEnum;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class IdStatusDto {

    private String id;

    public StatusEnum status;

    public IdStatusDto(String id,
                       StatusEnum status) {
        this.id = id;
        this.status = status;
    }
}
