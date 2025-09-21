package com.machine.app.openapi.iam.role.controller.vo.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class OpenApiRoleIdRequestVo {

    @NotBlank(message = "角色ID不能为空")
    @Schema(description = "角色ID")
    private String id;
}
