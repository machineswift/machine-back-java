package com.machine.app.openapi.iam.permission.controller.vo.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class OpenApiPermissionIdRequestVo {

    @NotBlank(message = "权限ID 不能为空")
    @Schema(description = "权限ID")
    private String id;
}
