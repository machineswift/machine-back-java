package com.machine.app.iam.user.controller.vo.request;

import com.machine.app.iam.userbk.vo.request.IamUserRoleUpdateRequestVo;
import com.machine.sdk.common.envm.iam.organization.IamOrganizationTypeEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;
import java.util.Set;

@Data
@Schema
@NoArgsConstructor
public class IamUserUpdatePermissionRequestVo {

    @NotBlank(message = "id不能为空")
    @Schema(description = "id", requiredMode = Schema.RequiredMode.REQUIRED)
    private String id;

    @Schema(description = "组织ID集合")
    private Map<IamOrganizationTypeEnum, Set<String>> organizationIdMap;

    @Schema(description = "用户角色集合")
    @NotNull(message = "用户角色不能为空")
    private List<IamUserRoleUpdateRequestVo> userRoleInfoList;
}
