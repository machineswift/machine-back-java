package com.machine.app.iam.user.controller.vo.request;

import com.machine.sdk.common.envm.crm.customer.GenderEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.LinkedHashSet;
import java.util.List;

@Data
@Schema
@NoArgsConstructor
public class IamSupplierUserCreateRequestVo {

    @NotBlank(message = "名称不能为空")
    @Schema(description = "名称")
    private String name;

    @NotBlank(message = "手机号不能为空")
    @Schema(description = "手机号")
    private String phone;

    @Schema(description = "性别(GenderEnum)")
    private GenderEnum gender;

    @Schema(description = "归属公司Id集合")
    private LinkedHashSet<String> companyIdSet;

    @Schema(description = "描述")
    private String description;

    @Schema(description = "用户角色集合")
    @NotNull(message = "用户角色不能为空")
    private List<IamUserRoleUpdateRequestVo> userRoleList;

}
