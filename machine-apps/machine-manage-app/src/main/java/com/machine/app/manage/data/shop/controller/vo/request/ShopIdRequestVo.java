package com.machine.app.manage.data.shop.controller.vo.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ShopIdRequestVo {

    @NotBlank(message = "门店ID不能为空")
    @Schema(description = "门店ID")
    private String id;
}
