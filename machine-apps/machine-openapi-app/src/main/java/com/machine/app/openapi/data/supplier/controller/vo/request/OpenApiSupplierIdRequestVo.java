package com.machine.app.openapi.data.supplier.controller.vo.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Schema
@NoArgsConstructor
public class OpenApiSupplierIdRequestVo {

    @NotNull(message = "供应商ID不能为空")
    @Schema(description = "供应商ID")
    private String id;
}
