package com.machine.app.openapi.data.supplier.controller.vo.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema
public class OpenApiSupplierListSimpleResponseVo {

    @Schema(description = "供应商ID")
    private String id;

    @Schema(description = "用户ID")
    private String userId;

}
