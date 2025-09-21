package com.machine.client.iam.auth.dto.output;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * OAuth2授权的数据传输对象 (DTO)
 */
@Data
public class IamOAuth2AuthorizationOutputDto {

    /**
     * 主键ID
     */
    private String id;

    /**
     * 注册客户端ID
     */
    private String registeredClientId;

    /**
     * 主体名称
     */
    private String principalName;

    /**
     * 授权授予类型
     */
    private String authorizationGrantType;

    /**
     * 授权的作用域
     */
    private String authorizedScopes;

    /**
     * 属性（二进制数据）
     */
    private String attributes;

    /**
     * 状态
     */
    private String state;

    /**
     * 授权码值（二进制数据）
     */
    private String authorizationCodeValue;

    /**
     * 授权码发放时间
     */
    private LocalDateTime authorizationCodeIssuedAt;

    /**
     * 授权码过期时间
     */
    private LocalDateTime authorizationCodeExpiresAt;

    /**
     * 授权码元数据（二进制数据）
     */
    private String authorizationCodeMetadata;

    /**
     * 访问令牌值（二进制数据）
     */
    private String accessTokenValue;

    /**
     * 访问令牌发放时间
     */
    private LocalDateTime accessTokenIssuedAt;

    /**
     * 访问令牌过期时间
     */
    private LocalDateTime accessTokenExpiresAt;

    /**
     * 访问令牌元数据（二进制数据）
     */
    private String accessTokenMetadata;

    /**
     * 访问令牌类型
     */
    private String accessTokenType;

    /**
     * 访问令牌作用域
     */
    private String accessTokenScopes;

    /**
     * OIDC ID令牌值（二进制数据）
     */
    private String oidcIdTokenValue;

    /**
     * OIDC ID令牌发放时间
     */
    private LocalDateTime oidcIdTokenIssuedAt;

    /**
     * OIDC ID令牌过期时间
     */
    private LocalDateTime oidcIdTokenExpiresAt;

    /**
     * OIDC ID令牌元数据（二进制数据）
     */
    private String oidcIdTokenMetadata;

    /**
     * 刷新令牌值（二进制数据）
     */
    private String refreshTokenValue;

    /**
     * 刷新令牌发放时间
     */
    private LocalDateTime refreshTokenIssuedAt;

    /**
     * 刷新令牌过期时间
     */
    private LocalDateTime refreshTokenExpiresAt;

    /**
     * 刷新令牌元数据（二进制数据）
     */
    private String refreshTokenMetadata;

    /**
     * 用户代码值（二进制数据）
     */
    private String userCodeValue;

    /**
     * 用户代码发放时间
     */
    private LocalDateTime userCodeIssuedAt;

    /**
     * 用户代码过期时间
     */
    private LocalDateTime userCodeExpiresAt;

    /**
     * 用户代码元数据（二进制数据）
     */
    private String userCodeMetadata;

    /**
     * 设备代码值（二进制数据）
     */
    private String deviceCodeValue;

    /**
     * 设备代码发放时间
     */
    private LocalDateTime deviceCodeIssuedAt;

    /**
     * 设备代码过期时间
     */
    private LocalDateTime deviceCodeExpiresAt;

    /**
     * 设备代码元数据（二进制数据）
     */
    private String deviceCodeMetadata;
}
