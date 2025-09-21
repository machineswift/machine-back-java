package com.machine.app.openapi.data.employee.controller.vo.response;

import com.machine.sdk.common.envm.data.DataShopEmployeeStatusEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class OpenApiShopEmployeeListSimpleResponseVo {

    @Schema(description = "门店员工ID")
    private String id;

    @Schema(description = "门店员工状态（ShopEmployeeStatusEnum）")
    private DataShopEmployeeStatusEnum employeeStatus;

    @Schema(description = "用户Id")
    private String userId;
}
