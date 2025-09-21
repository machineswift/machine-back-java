package com.machine.app.openapi.iam.user.controller.vo.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class OpenApiUserIdRequestVo {

    @NotBlank(message = "用户ID不能为空")
    @Schema(description = "用户ID")
    private String id;
}
