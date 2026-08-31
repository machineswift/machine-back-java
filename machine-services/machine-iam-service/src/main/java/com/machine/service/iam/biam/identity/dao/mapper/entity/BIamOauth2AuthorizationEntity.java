
package com.machine.service.iam.biam.identity.dao.mapper.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.machine.starter.mybatis.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * OAuth2授权的数据传输对象 (DTO)
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@TableName("t_biam_oauth2_authorization")
public class BIamOauth2AuthorizationEntity extends BaseEntity {

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
     * 授权范围
     */
    @TableField("authorized_scopes")
    private String authorizedScopes;

    /**
     * 属性
     */
    @TableField("attributes")
    private String attributes;

    /**
     * 状态
     */
    @TableField("state")
    private String state;

    /**
     * 授权码值
     */
    @TableField("authorization_code_value")
    private String authorizationCodeValue;

    /**
     * 授权码发放时间
     */
    @TableField("authorization_code_issued_at")
    private Long authorizationCodeIssuedAt;

    /**
     * 授权码过期时间
     */
    @TableField("authorization_code_expires_at")
    private Long authorizationCodeExpiresAt;

    /**
     * 授权码元数据
     */
    @TableField("authorization_code_metadata")
    private String authorizationCodeMetadata;

    /**
     * 访问令牌值
     */
    @TableField("access_token_value")
    private String accessTokenValue;

    /**
     * 访问令牌发放时间
     */
    @TableField("access_token_issued_at")
    private Long accessTokenIssuedAt;

    /**
     * 访问令牌过期时间
     */
    @TableField("access_token_expires_at")
    private Long accessTokenExpiresAt;

    /**
     * 访问令牌元数据
     */
    @TableField("access_token_metadata")
    private String accessTokenMetadata;

    /**
     * 访问令牌类型
     */
    @TableField("access_token_type")
    private String accessTokenType;

    /**
     * 访问令牌范围
     */
    @TableField("access_token_scopes")
    private String accessTokenScopes;

    /**
     * OpenID Connect ID 令牌值
     */
    @TableField("oidc_id_token_value")
    private String oidcIdTokenValue;

    /**
     * OpenID Connect ID 令牌发放时间
     */
    @TableField("oidc_id_token_issued_at")
    private Long oidcIdTokenIssuedAt;

    /**
     * OpenID Connect ID 令牌过期时间
     */
    @TableField("oidc_id_token_expires_at")
    private Long oidcIdTokenExpiresAt;

    /**
     * OpenID Connect ID 令牌元数据
     */
    @TableField("oidc_id_token_metadata")
    private String oidcIdTokenMetadata;

    /**
     * 刷新令牌值
     */
    @TableField("refresh_token_value")
    private String refreshTokenValue;

    /**
     * 刷新令牌发放时间
     */
    @TableField("refresh_token_issued_at")
    private Long refreshTokenIssuedAt;

    /**
     * 刷新令牌过期时间
     */
    @TableField("refresh_token_expires_at")
    private Long refreshTokenExpiresAt;

    /**
     * 刷新令牌元数据
     */
    @TableField("refresh_token_metadata")
    private String refreshTokenMetadata;

    /**
     * 用户码值
     */
    @TableField("user_code_value")
    private String userCodeValue;

    /**
     * 用户码发放时间
     */
    @TableField("user_code_issued_at")
    private Long userCodeIssuedAt;

    /**
     * 用户码过期时间
     */
    @TableField("user_code_expires_at")
    private Long userCodeExpiresAt;

    /**
     * 用户码元数据
     */
    @TableField("user_code_metadata")
    private String userCodeMetadata;

    /**
     * 设备码值
     */
    @TableField("device_code_value")
    private String deviceCodeValue;

    /**
     * 设备码发放时间
     */
    @TableField("device_code_issued_at")
    private Long deviceCodeIssuedAt;

    /**
     * 设备码过期时间
     */
    @TableField("device_code_expires_at")
    private Long deviceCodeExpiresAt;

    /**
     * 设备码元数据
     */
    @TableField("device_code_metadata")
    private String deviceCodeMetadata;
}