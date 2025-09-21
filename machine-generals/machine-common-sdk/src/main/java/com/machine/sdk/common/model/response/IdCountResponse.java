package com.machine.sdk.common.model.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Schema
@NoArgsConstructor
public class IdCountResponse {

    @Schema(description = "ID")
    public String id;

    @Schema(description = "数量")
    public Integer count;
}
