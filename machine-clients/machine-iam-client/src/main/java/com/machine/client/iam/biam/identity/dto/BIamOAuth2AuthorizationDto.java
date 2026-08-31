package com.machine.client.iam.biam.identity.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class BIamOAuth2AuthorizationDto {

    @Schema(description = "授权记录ID")
    private String id;

    @Schema(description = "注册客户端ID")
    private String registeredClientId;

    @Schema(description = "主体名称")
    private String principalName;

    @Schema(description = "授权授予类型")
    private String authorizationGrantType;

    @Schema(description = "授权范围")
    private String authorizedScopes;

    @Schema(description = "属性")
    private String attributes;

    @Schema(description = "状态")
    private String state;

    @Schema(description = "授权码值")
    private String authorizationCodeValue;

    @Schema(description = "授权码发放时间(时间戳)")
    private Long authorizationCodeIssuedAt;

    @Schema(description = "授权码过期时间(时间戳)")
    private Long authorizationCodeExpiresAt;

    @Schema(description = "授权码元数据")
    private String authorizationCodeMetadata;

    @Schema(description = "访问令牌值")
    private String accessTokenValue;

    @Schema(description = "访问令牌发放时间(时间戳)")
    private Long accessTokenIssuedAt;

    @Schema(description = "访问令牌过期时间(时间戳)")
    private Long accessTokenExpiresAt;

    @Schema(description = "访问令牌元数据")
    private String accessTokenMetadata;

    @Schema(description = "访问令牌类型")
    private String accessTokenType;

    @Schema(description = "访问令牌范围")
    private String accessTokenScopes;

    @Schema(description = "OpenID Connect ID 令牌值")
    private String oidcIdTokenValue;

    @Schema(description = "OpenID Connect ID 令牌发放时间(时间戳)")
    private Long oidcIdTokenIssuedAt;

    @Schema(description = "OpenID Connect ID 令牌过期时间(时间戳)")
    private Long oidcIdTokenExpiresAt;

    @Schema(description = "OpenID Connect ID 令牌元数据")
    private String oidcIdTokenMetadata;

    @Schema(description = "刷新令牌值")
    private String refreshTokenValue;

    @Schema(description = "刷新令牌发放时间(时间戳)")
    private Long refreshTokenIssuedAt;

    @Schema(description = "刷新令牌过期时间(时间戳)")
    private Long refreshTokenExpiresAt;

    @Schema(description = "刷新令牌元数据")
    private String refreshTokenMetadata;

    @Schema(description = "用户码值")
    private String userCodeValue;

    @Schema(description = "用户码发放时间(时间戳)")
    private Long userCodeIssuedAt;

    @Schema(description = "用户码过期时间(时间戳)")
    private Long userCodeExpiresAt;

    @Schema(description = "用户码元数据")
    private String userCodeMetadata;

    @Schema(description = "设备码值")
    private String deviceCodeValue;

    @Schema(description = "设备码发放时间(时间戳)")
    private Long deviceCodeIssuedAt;

    @Schema(description = "设备码过期时间(时间戳)")
    private Long deviceCodeExpiresAt;

    @Schema(description = "设备码元数据")
    private String deviceCodeMetadata;
}
