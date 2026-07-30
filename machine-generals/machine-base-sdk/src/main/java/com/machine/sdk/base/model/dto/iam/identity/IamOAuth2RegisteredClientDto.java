package com.machine.sdk.base.model.dto.iam.identity;

import com.machine.sdk.base.envm.StatusEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Set;

/**
 * OAuth2注册客户端
 */
@Data
@NoArgsConstructor
public class IamOAuth2RegisteredClientDto {

    @Schema(description = "主键ID")
    private String id;

    @Schema(description = "状态")
    private StatusEnum status;

    @Schema(description = "客户端ID")
    private String clientId;

    @Schema(description = "客户端ID发放时间")
    private Long clientIdIssuedAt;

    @Schema(description = "客户端密钥")
    private String clientSecret;

    @Schema(description = "客户端密钥过期时间")
    private Long clientSecretExpiresAt;

    @Schema(description = "客户端名称")
    private String clientName;

    @Schema(description = "客户端认证方法")
    private List<String> clientAuthenticationMethods;

    @Schema(description = "授权授予类型")
    private List<String> authorizationGrantTypes;

    @Schema(description = "重定向URI")
    private  List<String>  redirectUris;

    @Schema(description = "登出后重定向URI")
    private  List<String>  postLogoutRedirectUris;

    @Schema(description = "客户端作用域")
    private Set<String> scopes;

    @Schema(description = "客户端设置")
    private IamAuth2RegisteredClientSettingDto clientSettings;

    @Schema(description = "令牌设置")
    private IamAuth2RegisteredTokenSettingDto tokenSettings;

}
