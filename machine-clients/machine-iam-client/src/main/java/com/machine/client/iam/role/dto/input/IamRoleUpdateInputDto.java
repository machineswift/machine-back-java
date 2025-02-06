package com.machine.client.iam.role.dto.input;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.util.Map;
import java.util.Set;

@Data
public class IamRoleUpdateInputDto {

    @Schema(description = "ID")
    @NotBlank(message = "id不能为空")
    private String id;

    @Schema(description = "名称")
    @NotBlank(message = "名称不能为空")
    private String name;

    @Schema(description = "职务ID集合")
    private Set<String> jobPostIdSet;

    @Schema(description = "描述")
    private String description;

    @Schema(description = "权限Id集合")
    private Set<String> permissionIdSet;

    @Schema(description = "数据权限")
    private Map<String, String> dataPermissionMap;
}
