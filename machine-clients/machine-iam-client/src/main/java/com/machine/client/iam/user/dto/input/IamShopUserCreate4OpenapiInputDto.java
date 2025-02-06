package com.machine.client.iam.user.dto.input;

import com.machine.sdk.common.envm.crm.customer.GenderEnum;
import com.machine.sdk.common.model.dto.data.certificate.HealthCertificateDto;
import com.machine.sdk.common.model.dto.data.certificate.IdentityCardDto;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class IamShopUserCreate4OpenapiInputDto {

    @NotBlank(message = "名称不能为空")
    @Schema(description = "名称", requiredMode = Schema.RequiredMode.REQUIRED)
    private String name;

    @NotNull(message = "性别不能为空")
    @Schema(description = "性别(GenderEnum)")
    private GenderEnum gender;

    @NotBlank(message = "手机号不能为空")
    @Schema(description = "手机号")
    private String phone;

    @NotBlank(message = "门店编码不能为空")
    @Schema(description = "门店编码")
    private String shopCode;

    @NotBlank(message = "角色编码不能为空")
    @Schema(description = "角色编码（只能其中一个。 店长:STORE_MANAGER、店员:SALES_CLERK）")
    private String roleCode;

    @Schema(description = "身份证")
    private IdentityCardDto identityCard;

    @Schema(description = "健康证")
    private HealthCertificateDto healthCertificate;

    @Schema(description = "描述")
    private String description;

}
