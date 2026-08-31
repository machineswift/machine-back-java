package com.machine.client.iam.biam.role.dto.input;

import com.machine.sdk.base.envm.biam.role.BIamRoleTypeEnum;
import com.machine.sdk.base.model.dto.biam.auth.BIamDataPermissionRuleDto;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Schema
@NoArgsConstructor
public class BIamRoleCreateInputDto {

    @NotNull(message = "类型不能为空")
    private BIamRoleTypeEnum type;

    @NotBlank(message = "名称不能为空")
    private String name;

    @Schema(description = "描述")
    private String description;

    @NotNull(message = "数据权限不能为空")
    @Schema(description = "数据权限")
    private BIamDataPermissionRuleDto dataPermissionRule;

}
