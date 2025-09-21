package com.machine.app.openapi.data.employee.controller.vo.response;

import com.machine.sdk.common.envm.hrm.HrmEmployeeStatusEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class OpenApiCompanyEmployeeListSimpleResponseVo {

    @Schema(description = "公司员工ID")
    private String id;

    @Schema(description = "公司员工状态（CompanyEmployeeStatusEnum）")
    private HrmEmployeeStatusEnum employeeStatus;

    @Schema(description = "用户Id")
    private String userId;
}
