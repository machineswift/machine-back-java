package com.machine.client.iam.role.dto.input;

import com.machine.sdk.common.model.dto.iam.DataPermissionRuleDto;
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
public class IamRoleUpdateInputDto {

    @Schema(description = "ID")
    @NotBlank(message = "id不能为空")
    private String id;

    @Schema(description = "名称")
    @NotBlank(message = "名称不能为空")
    private String name;

    @Schema(description = "描述")
    private String description;

    @NotNull(message = "数据权限不能为空")
    @Schema(description = "数据权限")
    private DataPermissionRuleDto dataPermissionRule;
}
