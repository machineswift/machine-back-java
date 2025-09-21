
package com.machine.service.iam.auth.dao.mapper.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.machine.starter.mybatis.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * OAuth2授权的数据传输对象 (DTO)
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@TableName("t_iam_oauth2_authorization")
public class IamOauth2AuthorizationEntity extends BaseEntity {

    /**
     * 注册客户端ID
     */
    @TableField("registered_client_id")
    private String registeredClientId;

    /**
     * 主体名称
     */
    @TableField("principal_name")
    private String principalName;

    /**
     * 授权授予类型
     */
    @TableField("authorization_grant_type")
    private String authorizationGrantType;

    /**
     * 授权的作用域
     */
    @TableField("authorized_scopes")
    private String authorizedScopes;

    /**
     * 属性（二进制数据）
     */
    @TableField("attributes")
    private String attributes;

    /**
     * 状态
     */
    @TableField("state")
    private String state;

    /**
     * 授权码值（二进制数据）
     */
    @TableField("authorization_code_value")
    private String authorizationCodeValue;

    /**
     * 授权码发放时间
     */
    @TableField("authorization_code_issued_at")
    private long authorizationCodeIssuedAt;

    /**
     * 授权码过期时间
     */
    @TableField("authorization_code_expires_at")
    private long authorizationCodeExpiresAt;

    /**
     * 授权码元数据（二进制数据）
     */
    @TableField("authorization_code_metadata")
    private String authorizationCodeMetadata;

    /**
     * 访问令牌值（二进制数据）
     */
    @TableField("access_token_value")
    private String accessTokenValue;

    /**
     * 访问令牌发放时间
     */
    @TableField("access_token_issued_at")
    private long accessTokenIssuedAt;

    /**
     * 访问令牌过期时间
     */
    @TableField("access_token_expires_at")
    private long accessTokenExpiresAt;

    /**
     * 访问令牌元数据（二进制数据）
     */
    @TableField("access_token_metadata")
    private String accessTokenMetadata;

    /**
     * 访问令牌类型
     */
    @TableField("access_token_type")
    private String accessTokenType;

    /**
     * 访问令牌作用域
     */
    @TableField("access_token_scopes")
    private String accessTokenScopes;

    /**
     * OIDC ID令牌值（二进制数据）
     */
    @TableField("oidc_id_token_value")
    private String oidcIdTokenValue;

    /**
     * OIDC ID令牌发放时间
     */
    @TableField("oidc_id_token_issued_at")
    private long oidcIdTokenIssuedAt;

    /**
     * OIDC ID令牌过期时间
     */
    @TableField("oidc_id_token_expires_at")
    private long oidcIdTokenExpiresAt;

    /**
     * OIDC ID令牌元数据（二进制数据）
     */
    @TableField("oidc_id_token_metadata")
    private String oidcIdTokenMetadata;

    /**
     * 刷新令牌值（二进制数据）
     */
    @TableField("refresh_token_value")
    private String refreshTokenValue;

    /**
     * 刷新令牌发放时间
     */
    @TableField("refresh_token_issued_at")
    private LocalDateTime refreshTokenIssuedAt;

    /**
     * 刷新令牌过期时间
     */
    @TableField("refresh_token_expires_at")
    private LocalDateTime refreshTokenExpiresAt;

    /**
     * 刷新令牌元数据（二进制数据）
     */
    @TableField("refresh_token_metadata")
    private String refreshTokenMetadata;

    /**
     * 用户代码值（二进制数据）
     */
    @TableField("user_code_value")
    private String userCodeValue;

    /**
     * 用户代码发放时间
     */
    @TableField("user_code_issued_at")
    private LocalDateTime userCodeIssuedAt;

    /**
     * 用户代码过期时间
     */
    @TableField("user_code_expires_at")
    private LocalDateTime userCodeExpiresAt;

    /**
     * 用户代码元数据（二进制数据）
     */
    @TableField("user_code_metadata")
    private String userCodeMetadata;

    /**
     * 设备代码值（二进制数据）
     */
    @TableField("device_code_value")
    private String deviceCodeValue;

    /**
     * 设备代码发放时间
     */
    @TableField("device_code_issued_at")
    private LocalDateTime deviceCodeIssuedAt;

    /**
     * 设备代码过期时间
     */
    @TableField("device_code_expires_at")
    private LocalDateTime deviceCodeExpiresAt;

    /**
     * 设备代码元数据（二进制数据）
     */
    @TableField("device_code_metadata")
    private String deviceCodeMetadata;
}