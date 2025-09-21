package com.machine.app.openapi.data.shop.controller.vo.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@NoArgsConstructor
public class OpenApiShopCodeSetRequestVo {

    @NotEmpty(message = "门店编码集合不能为空")
    @Schema(description = "门店编码集合", requiredMode = Schema.RequiredMode.REQUIRED)
    private Set<String> codeSet;

}
