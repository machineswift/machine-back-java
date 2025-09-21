package com.machine.sdk.common.model.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Schema
@NoArgsConstructor
public class PageRequest {
    @Schema(description = "当前页，默认1")
    private Long current = 1L;

    @Schema(description = "每页的数量，默认20")
    private Long size = 20L;
}
