package com.machine.sdk.base.model.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class IdNameDto {

    private String id;

    private String name;

    public IdNameDto(String id,
                     String name) {
        this.id = id;
        this.name = name;
    }
}
