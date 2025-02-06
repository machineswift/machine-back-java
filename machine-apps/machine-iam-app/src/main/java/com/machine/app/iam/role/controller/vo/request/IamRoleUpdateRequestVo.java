package com.machine.app.iam.role.controller.vo.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;
import java.util.Set;

@Data
@Schema
@NoArgsConstructor
public class IamRoleUpdateRequestVo {

    @NotBlank(message = "id不能为空")
    @Schema(description = "id", requiredMode = Schema.RequiredMode.REQUIRED)
    private String id;

    @NotBlank(message = "名称不能为空")
    @Schema(description = "名称", requiredMode = Schema.RequiredMode.REQUIRED)
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
