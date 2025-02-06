package com.machine.app.openapi.data.shop.controller.vo.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class OpenApiShopLabelOptionIdRequestVo {

    @NotNull(message = "标签选项ID不能为空")
    @Schema(description = "标签选项ID")
    private String id;
}
