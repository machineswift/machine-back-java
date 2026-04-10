package com.machine.sdk.base.model.dto;

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
