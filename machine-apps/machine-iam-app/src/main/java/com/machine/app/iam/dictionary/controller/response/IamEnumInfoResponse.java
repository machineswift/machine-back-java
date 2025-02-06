package com.machine.app.iam.dictionary.controller.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema
public class IamEnumInfoResponse {
    @Schema(description = "编码")
    private String code;

    @Schema(description = "说明")
    private String msg;
}
