package com.machine.client.iam.userbk.dto.input;

import com.machine.client.iam.user.dto.input.IamUserRoleInfoUpdateInputDto;
import com.machine.sdk.common.envm.common.GenderEnum;
import com.machine.sdk.common.model.dto.data.certificate.HealthCertificateDto;
import com.machine.sdk.common.model.dto.data.certificate.IdentityCardDto;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class IamShopUserUpdateInputDto {

    @NotBlank(message = "id不能为空")
    private String id;

    @NotBlank(message = "名称不能为空")
    private String name;

    /**
     * 手机号
     */
    private String phone;

    /**
     * 性别
     */
    private GenderEnum gender;

    /**
     * 身份证
     */
    private IdentityCardDto identityCard;

    /**
     * 健康证
     */
    private HealthCertificateDto healthCertificate;

    /**
     * 描述
     */
    private String description;

    @NotNull(message = "用户角色不能为空")
    private List<IamUserRoleInfoUpdateInputDto> userRoleList;
}
