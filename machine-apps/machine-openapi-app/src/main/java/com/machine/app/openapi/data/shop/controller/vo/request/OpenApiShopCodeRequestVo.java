package com.machine.app.openapi.data.shop.controller.vo.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class OpenApiShopCodeRequestVo {

    @NotBlank(message = "门店编码不能为空")
    @Schema(description = "门店编码", requiredMode = Schema.RequiredMode.REQUIRED)
    private String code;

}
