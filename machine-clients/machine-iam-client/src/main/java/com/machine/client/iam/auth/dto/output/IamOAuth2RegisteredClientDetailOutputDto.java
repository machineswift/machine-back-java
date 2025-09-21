package com.machine.client.iam.auth.dto.output;

import com.machine.sdk.common.envm.StatusEnum;
import com.machine.sdk.common.model.dto.data.WebHookInfoDto;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * OAuth2注册客户端的数据传输对象 (DTO)
 */
@Data
public class IamOAuth2RegisteredClientDetailOutputDto {

    /**
     * 主键ID
     */
    private String id;

    /**
     * 状态
     */
    private StatusEnum status;

    /**
     * 客户端ID
     */
    private String clientId;

    /**
     * 客户端ID发放时间，默认为当前时间
     */
    private LocalDateTime clientIdIssuedAt;

    /**
     * 客户端密钥
     */
    private String clientSecret;

    /**
     * 客户端密钥过期时间
     */
    private LocalDateTime clientSecretExpiresAt;

    /**
     * 客户端名称
     */
    private String clientName;

    /**
     * 客户端认证方法
     */
    private String clientAuthenticationMethods;

    /**
     * 授权授予类型
     */
    private String authorizationGrantTypes;

    /**
     * 重定向URI
     */
    private String redirectUris;

    /**
     * 登出后重定向URI
     */
    private String postLogoutRedirectUris;

    /**
     * 客户端作用域
     */
    private String scopes;

    /**
     * 客户端设置
     */
    private String clientSettings;

    /**
     * 令牌设置
     */
    private String tokenSettings;

    /**
     * WebHook信息
     */
    private WebHookInfoDto webHookInfo;
}
