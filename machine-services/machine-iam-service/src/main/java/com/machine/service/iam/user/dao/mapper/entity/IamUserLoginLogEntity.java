package com.machine.service.iam.user.dao.mapper.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.machine.sdk.common.envm.iam.auth.IamAuthActionEnum;
import com.machine.sdk.common.envm.iam.auth.IamAuthMethodEnum;
import com.machine.sdk.common.envm.iam.auth.IamAuthResultEnum;
import com.machine.starter.mybatis.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@TableName("t_iam_user_login_log")
@EqualsAndHashCode(callSuper = true)
public class IamUserLoginLogEntity extends BaseEntity {
    /**
     * 用户id
     */
    @TableField("user_id")
    private String userId;

    /**
     * 用户名（系统账号）
     */
    @TableField("username")
    private String username;

    /**
     * 操作
     */
    @TableField("auth_action")
    private IamAuthActionEnum authAction;

    /**
     * 登录方式
     */
    @TableField("auth_method")
    private IamAuthMethodEnum authMethod;

    /**
     * 结果
     */
    @TableField("auth_result")
    private IamAuthResultEnum authResult;

    /**
     * 编码（工号）
     */
    @TableField("code")
    private String code;

    /**
     * 手机号
     */
    @TableField("phone")
    private String phone;

    /**
     * 姓名
     */
    @TableField("name")
    private String name;

    /**
     * IP 地址
     */
    @TableField("ip_address")
    private String ipAddress;

    /**
     * 平台
     */
    @TableField("platform")
    private String platform;

    /**
     * access token id
     */
    @TableField("access_token_id")
    private String accessTokenId;

    /**
     * refresh token id
     */
    @TableField("refresh_token_id")
    private String refreshTokenId;

    /**
     * access token 过期时间
     */
    @TableField("access_token_expire")
    private Long accessTokenExpire;

    /**
     * refresh token 过期时间
     */
    @TableField("refresh_token_expire")
    private Long refreshTokenExpire;

    /**
     * access token
     */
    @TableField("access_token")
    private String accessToken;

    /**
     * refresh token
     */
    @TableField("refresh_token")
    private String refreshToken;

    /**
     * 浏览器和操作系统等信息
     */
    @TableField("user_agent")
    private String userAgent;

    /**
     * 失败原因
     */
    @TableField("fail_reason")
    private String failReason;

    /**
     * 描述
     */
    @TableField("description")
    private String description;
}
