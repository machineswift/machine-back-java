package com.machine.sdk.common.model.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class IdDto {

    private String id;

    public IdDto(String id) {
        this.id = id;
    }
}
