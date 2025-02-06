package com.machine.client.iam.user.dto.input;

import com.machine.sdk.common.envm.iam.auth.AuthActionEnum;
import com.machine.sdk.common.envm.iam.auth.AuthMethodEnum;
import com.machine.sdk.common.envm.iam.auth.AuthResultEnum;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class IamUserLoginLogCreateInputDto {

    /**
     * 用户id
     */
    private String userId;

    /**
     * 系统账号(用户名)
     */
    private String userName;

    /**
     * 操作(AuthActionEnum)
     */
    private AuthActionEnum authAction;

    /**
     * 登录方式(AuthMethodEnum)
     */
    private AuthMethodEnum authMethod;

    /**
     * 结果(AuthResultEnum)
     */
    private AuthResultEnum authResult;

    /**
     * 编码（工号）
     */
    private String code;

    /**
     * 手机号
     */
    private String phone;

    /**
     * 姓名
     */
    private String name;

    /**
     * IP 地址
     */
    private String ipAddress;

    /**
     * 平台
     */
    private String platform;

    /**
     * 浏览器和操作系统等信息
     */
    private String userAgent;

    /**
     * 失败原因
     */
    private String failReason;

    private String accessTokenId;
    private String refreshTokenId;
    private Long accessTokenExpire;
    private Long refreshTokenExpire;
    private String accessToken;
    private String refreshToken;
    private String description;
}
