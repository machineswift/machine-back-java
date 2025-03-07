package com.machine.app.openapi.data.employee.controller.vo.request;

import com.machine.sdk.common.envm.system.DataPersistenceStatusEnum;
import com.machine.sdk.common.model.dto.data.certificate.HealthCertificateDto;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Schema
@NoArgsConstructor
public class OpenApiShopEmployeeUpdateHealthCertificateRequestVo {

    @NotBlank(message = "门店员工ID不能为空")
    @Schema(description = "门店员工ID", requiredMode = Schema.RequiredMode.REQUIRED)
    private String id;

    @NotNull(message = "数据持久话状态不能为空")
    @Schema(description = "数据持久话状态(DataPersistenceStatusEnum)，（未审核传:TEMPORARY，审核通过传:PERMANENT）", requiredMode = Schema.RequiredMode.REQUIRED)
    private DataPersistenceStatusEnum persistenceStatus;

    @Valid
    @NotNull(message = "健康证不能为空")
    @Schema(description = "健康证", requiredMode = Schema.RequiredMode.REQUIRED)
    private HealthCertificateDto healthCertificate;
}
