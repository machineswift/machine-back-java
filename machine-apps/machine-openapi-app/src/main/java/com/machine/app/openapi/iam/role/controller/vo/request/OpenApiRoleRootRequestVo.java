package com.machine.app.openapi.iam.role.controller.vo.request;

import com.machine.sdk.common.envm.iam.role.IamRoleTypeEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class OpenApiRoleRootRequestVo {

    @NotNull(message = "角色类型不能为空")
    @Schema(description = "角色类型 (RoleTypeEnum)")
    private IamRoleTypeEnum type;
}
