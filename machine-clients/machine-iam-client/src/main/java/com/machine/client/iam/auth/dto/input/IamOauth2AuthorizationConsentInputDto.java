
package com.machine.client.iam.auth.dto.input;

import lombok.Data;

/**
 * OAuth2 授权同意实体类
 */
@Data
public class IamOauth2AuthorizationConsentInputDto {

    /**
     * 注册客户端ID
     * <p>
     * 用于标识授权同意的客户端
     */
    private String registeredClientId;

    /**
     * 主体名称
     * <p>
     * 用于标识授权同意的用户或主体
     */
    private String principalName;

    /**
     * 权限列表
     * <p>
     * 包含用户授予的权限，以逗号分隔的字符串形式存储
     */
    private String authorities;
}