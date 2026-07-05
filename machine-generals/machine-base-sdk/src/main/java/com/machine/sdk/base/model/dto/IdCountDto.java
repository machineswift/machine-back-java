package com.machine.sdk.base.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Schema
@NoArgsConstructor
public class IdCountDto {

    @Schema(description = "ID")
    private String id;

    @Schema(description = "数量")
    private Long count;

}
