package com.machine.app.iam.biam.role.controller.vo.request;

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
public class BIamRoleCreateRequestVo {

    @NotNull(message = "类型不能为空")
    @Schema(description = "类型（IamRoleTypeEnum）", requiredMode = Schema.RequiredMode.REQUIRED)
    private BIamRoleTypeEnum type;

    @NotBlank(message = "名称不能为空")
    @Schema(description = "名称", requiredMode = Schema.RequiredMode.REQUIRED)
    private String name;

    @Schema(description = "描述")
    private String description;

    @NotNull(message = "数据权限不能为空")
    @Schema(description = "数据权限")
    private BIamDataPermissionRuleDto dataPermissionRule;

}
