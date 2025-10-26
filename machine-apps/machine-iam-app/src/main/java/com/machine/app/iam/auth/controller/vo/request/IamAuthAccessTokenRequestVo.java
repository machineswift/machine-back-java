package com.machine.app.iam.auth.controller.vo.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Schema
@NoArgsConstructor
public class IamAuthAccessTokenRequestVo {

    @NotBlank(message = "refreshToken不能为空")
    @Schema(description = "refreshToken", requiredMode = Schema.RequiredMode.REQUIRED)
    private String refreshToken;
}
