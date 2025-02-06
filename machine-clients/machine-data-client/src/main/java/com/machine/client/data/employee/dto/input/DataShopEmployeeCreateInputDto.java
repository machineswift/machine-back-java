package com.machine.client.data.employee.dto.input;

import com.machine.sdk.common.envm.data.ShopEmployeeStatusEnum;
import com.machine.sdk.common.model.dto.data.certificate.HealthCertificateDto;
import com.machine.sdk.common.model.dto.data.certificate.IdentityCardDto;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class DataShopEmployeeCreateInputDto {

    @NotBlank(message = "用户Id不能为空")
    private String userId;

    @NotNull(message = "员工状态不能为空")
    private ShopEmployeeStatusEnum employeeStatus;

    /**
     * 身份证
     */
    private IdentityCardDto identityCard;

    /**
     * 健康证
     */
    private HealthCertificateDto healthCertificate;

}
