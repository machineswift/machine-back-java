package com.machine.app.iam.userbk.vo.request;

import com.machine.sdk.common.envm.common.GenderEnum;
import com.machine.sdk.common.model.dto.data.certificate.HealthCertificateDto;
import com.machine.sdk.common.model.dto.data.certificate.IdentityCardDto;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Schema
@NoArgsConstructor
public class IamShopUserUpdateRequestVo {

    @NotBlank(message = "id不能为空")
    @Schema(description = "id", requiredMode = Schema.RequiredMode.REQUIRED)
    private String id;

    @NotBlank(message = "名称不能为空")
    @Schema(description = "名称", requiredMode = Schema.RequiredMode.REQUIRED)
    private String name;

    @Schema(description = "手机号")
    private String phone;

    @Schema(description = "性别(GenderEnum)")
    private GenderEnum gender;

    @Schema(description = "身份证")
    private IdentityCardDto identityCard;

    @Schema(description = "健康证")
    private HealthCertificateDto healthCertificate;

    @Schema(description = "描述")
    private String description;

    @Schema(description = "用户角色集合")
    @NotNull(message = "用户角色不能为空")
    private List<IamUserRoleUpdateRequestVo> userRoleList;
}
