package com.machine.app.openapi.data.employee.controller.vo.response;

import com.machine.sdk.common.envm.data.CompanyEmployeeStatusEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class OpenapiCompanyEmployeeDetailResponseVo {

    @Schema(description = "公司员工ID")
    private String id;

    @Schema(description = "公司员工状态（ShopEmployeeStatusEnum）")
    private CompanyEmployeeStatusEnum employeeStatus;

    @Schema(description = "用户Id")
    private String userId;

    @Schema(description = "创建时间（Unix 时间戳）")
    private Long createTime;

    @Schema(description = "更新时间（Unix 时间戳）")
    private Long updateTime;
}
