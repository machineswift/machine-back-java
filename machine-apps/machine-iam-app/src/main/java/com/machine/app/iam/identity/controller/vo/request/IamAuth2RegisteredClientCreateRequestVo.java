package com.machine.app.iam.identity.controller.vo.request;

import com.machine.sdk.base.annotation.StrongPassword;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Set;

@Data
@Schema
@NoArgsConstructor
public class IamAuth2RegisteredClientCreateRequestVo {

    @Schema(description = "clientName")
    @NotBlank(message = "clientName 不能为空")
    private String clientName;

    @ToString.Exclude
    @Schema(description = "客户端密钥")
    @NotBlank(message = "客户端密钥不能为空")
    @StrongPassword
    private String clientSecret;

    @NotEmpty(message = "scopes 不能为空")
    @Schema(description = "作用域列表")
    private Set<String> scopes;

}
