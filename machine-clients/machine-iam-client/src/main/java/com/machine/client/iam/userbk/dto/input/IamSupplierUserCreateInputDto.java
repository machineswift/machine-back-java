package com.machine.client.iam.userbk.dto.input;

import com.machine.client.iam.user.dto.input.IamUserRoleInfoUpdateInputDto;
import com.machine.sdk.common.envm.common.GenderEnum;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.LinkedHashSet;
import java.util.List;

@Data
@NoArgsConstructor
public class IamSupplierUserCreateInputDto {

    @NotBlank(message = "名称不能为空")
    private String name;

    @NotBlank(message = "手机号不能为空")
    private String phone;

    /**
     * 性别
     */
    private GenderEnum gender;

    /**
     * 归属公司Id集合
     */
    private LinkedHashSet<String> companyIdSet;

    /**
     * 描述
     */
    private String description;

    @NotNull(message = "用户角色不能为空")
    private List<IamUserRoleInfoUpdateInputDto> userRoleList;
}
