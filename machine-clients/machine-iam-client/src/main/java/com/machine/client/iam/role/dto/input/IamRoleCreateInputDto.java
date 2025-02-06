package com.machine.client.iam.role.dto.input;

import com.machine.sdk.common.envm.iam.role.RoleTypeEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.Map;
import java.util.Set;

@Data
public class IamRoleCreateInputDto {

    @NotNull(message = "类型不能为空")
    private RoleTypeEnum type;

    @NotBlank(message = "名称不能为空")
    private String name;

    @Schema(description = "职务ID集合")
    private Set<String> jobPostIdSet;

    @Schema(description = "描述")
    private String description;

    @Schema(description = "权限Id列表")
    private Set<String> permissionIdSet;

    @Schema(description = "数据权限")
    private Map<String, String> dataPermissionMap;

}
