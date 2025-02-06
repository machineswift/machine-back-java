package com.machine.app.openapi.data.shop.controller.vo.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class OpenApiShopIdRequestVo {

    @NotBlank(message = "门店Id不能为空")
    @Schema(description = "门店Id", requiredMode = Schema.RequiredMode.REQUIRED)
    private String id;

}
