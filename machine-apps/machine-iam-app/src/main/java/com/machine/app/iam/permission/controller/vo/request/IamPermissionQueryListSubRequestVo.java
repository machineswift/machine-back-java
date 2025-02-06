package com.machine.app.iam.permission.controller.vo.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Schema
@NoArgsConstructor
public class IamPermissionQueryListSubRequestVo {

    @NotBlank(message = "权限id不能为空")
    @Schema(description = "权限id", requiredMode = Schema.RequiredMode.REQUIRED)
    private String id;

}
