package com.machine.app.iam.auth.controller.vo.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@Schema
@NoArgsConstructor
public class IamAuthCreateClientRequestVo {

    @Schema(description = "clientId")
    @NotBlank(message = "clientId 不能为空")
    private String clientId;

    @Schema(description = "clientName")
    @NotBlank(message = "clientName 不能为空")
    private String clientName;

    @Schema(description = "password")
    @NotBlank(message = "password 不能为空")
    private String password;

    @Schema(description = "scopes")
    @NotEmpty(message = "scopes 不能为空")
    private Set<String> scopes;

    @Schema(description = "redirectUris")
    @NotEmpty(message = "redirectUris 不能为空")
    private Set<String> redirectUrls;

}
