package com.machine.client.iam.role.dto.input;

import com.machine.sdk.common.model.dto.iam.DataPermissionRuleDto;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;
import java.util.Set;

@Data
@Schema
@NoArgsConstructor
public class IamRoleUpdatePermissionInputDto {

    @NotBlank(message = "id不能为空")
    @Schema(description = "id", requiredMode = Schema.RequiredMode.REQUIRED)
    private String id;

    @Schema(description = "权限Id集合")
    private Set<String> permissionIdSet;

    @Schema(description = "菜单数据权限")
    private Map<String, List<DataPermissionRuleDto>> dataPermissionRuleMap;

}
