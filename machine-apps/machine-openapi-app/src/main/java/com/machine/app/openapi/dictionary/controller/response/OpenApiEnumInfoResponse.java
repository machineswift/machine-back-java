package com.machine.app.openapi.dictionary.controller.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Schema
@NoArgsConstructor
public class OpenApiEnumInfoResponse {
    @Schema(description = "编码")
    private String code;

    @Schema(description = "说明")
    private String message;
}
