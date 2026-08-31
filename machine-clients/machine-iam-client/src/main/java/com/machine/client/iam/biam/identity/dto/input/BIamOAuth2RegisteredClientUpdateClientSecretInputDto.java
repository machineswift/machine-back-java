package com.machine.client.iam.biam.identity.dto.input;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@Schema
@NoArgsConstructor
public class BIamOAuth2RegisteredClientUpdateClientSecretInputDto {

    @NotBlank(message = "id 不能为空")
    @Schema(description = "主键ID")
    private String id;

    @ToString.Exclude
    @NotEmpty(message = "客户端密钥不能为空")
    @Schema(description = "客户端密钥")
    private String clientSecret;

}
