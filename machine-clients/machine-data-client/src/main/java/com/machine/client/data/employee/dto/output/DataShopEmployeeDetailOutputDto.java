package com.machine.client.data.employee.dto.output;

import com.machine.sdk.common.envm.data.DataShopEmployeeStatusEnum;
import com.machine.sdk.common.model.dto.data.certificate.HealthCertificateDto;
import com.machine.sdk.common.model.dto.data.certificate.IdentityCardDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class DataShopEmployeeDetailOutputDto {

    @Schema(description = "id")
    private String id;

    @Schema(description = "用户Id")
    private String userId;

    @Schema(description = "员工状态")
    private DataShopEmployeeStatusEnum employeeStatus;

    @Schema(description = "身份证信息")
    private IdentityCardDto identityCard;

    @Schema(description = "健康证信息")
    private HealthCertificateDto healthCertificate;

    @Schema(description = "创建人")
    private String createBy;

    @Schema(description = "创建时间")
    private long createTime;

    @Schema(description = "修改人")
    private String updateBy;

    @Schema(description = "更新时间")
    private long updateTime;
}
