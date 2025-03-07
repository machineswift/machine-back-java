package com.machine.app.openapi.data.employee.controller.vo.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Schema
@NoArgsConstructor
public class OpenapiShopEmployeeUpdatePhoneRequestVo {

    @NotBlank(message = "门店员工ID不能为空")
    @Schema(description = "门店员工ID", requiredMode = Schema.RequiredMode.REQUIRED)
    private String id;

    @NotNull(message = "门店员工手机号不能为空")
    @Schema(description = "门店员工手机号", requiredMode = Schema.RequiredMode.REQUIRED)
    private String phone;

}
