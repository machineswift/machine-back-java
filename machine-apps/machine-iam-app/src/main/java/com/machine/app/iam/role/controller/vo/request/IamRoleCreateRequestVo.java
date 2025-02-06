package com.machine.app.iam.role.controller.vo.request;

import com.machine.sdk.common.envm.iam.role.RoleTypeEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;
import java.util.Set;

@Data
@Schema
@NoArgsConstructor
public class IamRoleCreateRequestVo {

    @NotNull(message = "类型不能为空")
    @Schema(description = "类型（RoleTypeEnum）", requiredMode = Schema.RequiredMode.REQUIRED)
    private RoleTypeEnum type;

    @NotBlank(message = "名称不能为空")
    @Schema(description = "名称", requiredMode = Schema.RequiredMode.REQUIRED)
    private String name;

    @Schema(description = "职务ID列表")
    private Set<String> jobPostIdSet;

    @Schema(description = "描述")
    private String description;

    @Schema(description = "权限Id列表")
    private Set<String> permissionIdSet;

    @Schema(description = "数据权限")
    private Map<String, String> dataPermissionMap;

}
