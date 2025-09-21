package com.machine.client.data.employee.dto.input;

import com.machine.sdk.common.envm.data.DataShopEmployeeStatusEnum;
import com.machine.sdk.common.model.dto.data.certificate.HealthCertificateDto;
import com.machine.sdk.common.model.dto.data.certificate.IdentityCardDto;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Schema
@NoArgsConstructor
public class DataShopEmployeeCreateInputDto {

    @NotBlank(message = "用户Id不能为空")
    private String userId;

    @NotNull(message = "员工状态不能为空")
    private DataShopEmployeeStatusEnum employeeStatus;

    @Schema(description = "身份证")
    private IdentityCardDto identityCard;

    @Schema(description = "健康证")
    private HealthCertificateDto healthCertificate;

}
