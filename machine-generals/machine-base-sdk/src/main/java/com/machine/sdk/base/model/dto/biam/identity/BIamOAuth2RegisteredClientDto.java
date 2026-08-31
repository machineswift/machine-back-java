package com.machine.sdk.base.model.dto.biam.identity;

import com.machine.sdk.base.envm.StatusEnum;
import com.machine.sdk.base.envm.biam.identity.BIamAuthorizationGrantTypeEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Set;
import java.util.TreeSet;

/**
 * OAuth2注册客户端
 */
@Data
@NoArgsConstructor
public class BIamOAuth2RegisteredClientDto {

    @Schema(description = "主键ID")
    private String id;

    @Schema(description = "状态")
    private StatusEnum status;

    @Schema(description = "授权类型")
    private BIamAuthorizationGrantTypeEnum authorizationGrantType;

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
    private List<String> redirectUris;

    @Schema(description = "登出后重定向URI")
    private List<String> postLogoutRedirectUris;

    @Schema(description = "客户端作用域")
    private Set<String> scopes;

    @Schema(description = "客户端设置")
    private BIamAuth2RegisteredClientSettingDto clientSettings;

    @Schema(description = "令牌设置")
    private BIamAuth2RegisteredTokenSettingDto tokenSettings;

    @Schema(description = "ip白名单")
    private TreeSet<String> allowedIps;

    @Schema(description = "更新时间")
    private Long updateTime;

}
