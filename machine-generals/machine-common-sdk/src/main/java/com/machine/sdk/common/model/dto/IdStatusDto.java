package com.machine.sdk.common.model.dto;

import com.machine.sdk.common.envm.StatusEnum;
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
