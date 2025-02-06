package com.machine.app.manage.hrm.department.controller.vo.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema
public class DepartmentChargeResponseVo {

    @Schema(description = "用户Id")
    private String userId;

    @Schema(description = "员工Id")
    private String employeeId;

    @Schema(description = "姓名")
    private String name;

}
