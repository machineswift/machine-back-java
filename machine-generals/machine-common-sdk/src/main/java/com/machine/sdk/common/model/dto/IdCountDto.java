package com.machine.sdk.common.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema
public class IdCountDto {

    @Schema(description = "ID")
    private String id;

    @Schema(description = "数量")
    private Long count;

}
