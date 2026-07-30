package com.machine.client.iam.identity.dto.input;

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
public class IamOAuth2RegisteredClientUpdateInputDto {

    @NotBlank(message = "id 不能为空")
    @Schema(description = "ID")
    private String id;

    @Schema(description = "clientName")
    @NotBlank(message = "clientName 不能为空")
    private String clientName;

    @ToString.Exclude
    @Schema(description = "客户端密钥")
    @NotBlank(message = "客户端密钥不能为空")
    private String clientSecret;

    @NotEmpty(message = "scopes 不能为空")
    @Schema(description = "作用域列表")
    private Set<String> scopes;

}
