package com.machine.service.iam.identity.dao.mapper.entiry;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.machine.sdk.base.envm.StatusEnum;
import com.machine.starter.mybatis.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * OAuth2 客户端信息表
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@TableName("t_iam_oauth2_registered_client")
public class IamOauth2RegisteredClientEntity extends BaseEntity {

    /**
     * 状态（DISABLE:禁用，ENABLE:启用）
     */
    @TableField("status")
    private StatusEnum status;

    /**
     * 客户端 ID
     */
    @TableField(value = "client_id")
    private String clientId;

    /**
     * 客户端 ID 发放时间（时间戳）
     */
    @TableField(value = "client_id_issued_at")
    private Long clientIdIssuedAt;

    /**
     * 客户端密钥（加密存储，可为空）
     */
    @TableField("client_secret")
    private String clientSecret;

    /**
     * 客户端密钥过期时间（时间戳，可为空）
     */
    @TableField("client_secret_expires_at")
    private Long clientSecretExpiresAt;

    /**
     * 客户端名称
     */
    @TableField("client_name")
    private String clientName;

    /**
     * 客户端认证方法
     */
    @TableField("client_authentication_methods")
    private String clientAuthenticationMethods;

    /**
     * 授权授予类型
     */
    @TableField("authorization_grant_types")
    private String authorizationGrantTypes;

    /**
     * 重定向 URI
     */
    @TableField("redirect_uris")
    private String redirectUris;

    /**
     * 注销后重定向 URI
     */
    @TableField("post_logout_redirect_uris")
    private String postLogoutRedirectUris;

    /**
     * 作用域
     */
    @TableField("scopes")
    private String scopes;

    /**
     * 客户端设置
     */
    @TableField("client_settings")
    private String clientSettings;

    /**
     * 令牌设置
     */
    @TableField("token_settings")
    private String tokenSettings;

    /**
     * WebHook 信息
     */
    @TableField("web_hook_info")
    private String webHookInfo;
}