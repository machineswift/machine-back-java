package com.machine.app.openapi.data.employee.controller.vo.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class OpenApiCompanyEmployeeIdRequestVo {

    @NotBlank(message = "公司员工ID不能为空")
    @Schema(description = "公司员工ID")
    private String id;

}
