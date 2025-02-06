package com.machine.client.iam.user.dto.output;

import com.machine.sdk.common.envm.iam.auth.AuthActionEnum;
import com.machine.sdk.common.envm.iam.auth.AuthMethodEnum;
import com.machine.sdk.common.envm.iam.auth.AuthResultEnum;
import lombok.Data;

@Data
public class UserLoginLogDetailOutputDto {
    /**
     * ID
     */
    private String id;

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

    private String accessToken;

    private Long accessTokenExpire;

    private String refreshTokenId;

    private String refreshToken;

    private Long refreshTokenExpire;

    /**
     * 创建人ID
     */
    private String createBy;


    /**
     * 创建时间（Unix 时间戳）
     */
    private Long createTime;

    /**
     * 操作人ID
     */
    private String updateBy;

    /**
     * 更新时间（Unix 时间戳）
     */
    private Long updateTime;

}
