package com.machine.app.openapi.data.employee.controller.vo.response;

import com.machine.sdk.common.envm.data.ShopEmployeeStatusEnum;
import com.machine.sdk.common.model.dto.data.certificate.HealthCertificateDto;
import com.machine.sdk.common.model.dto.data.certificate.IdentityCardDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class OpenapiShopEmployeeDetailResponseVo {

    @Schema(description = "门店员工ID")
    private String id;

    @Schema(description = "门店员工状态（ShopEmployeeStatusEnum）")
    private ShopEmployeeStatusEnum employeeStatus;

    @Schema(description = "用户Id")
    private String userId;

    @Schema(description = "身份证信息")
    private IdentityCardDto identityCard;

    @Schema(description = "健康证信息")
    private HealthCertificateDto healthCertificate;

    @Schema(description = "创建时间（Unix 时间戳）")
    private Long createTime;

    @Schema(description = "更新时间（Unix 时间戳）")
    private Long updateTime;
}
