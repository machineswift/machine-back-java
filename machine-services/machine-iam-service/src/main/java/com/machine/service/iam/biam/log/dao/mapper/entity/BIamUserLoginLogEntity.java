package com.machine.service.iam.biam.log.dao.mapper.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.machine.sdk.base.envm.biam.auth.BIamAuthActionEnum;
import com.machine.sdk.base.envm.biam.auth.BIamAuthMethodEnum;
import com.machine.sdk.base.envm.biam.auth.BIamAuthResultEnum;
import com.machine.starter.mybatis.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@TableName("t_biam_user_login_log")
@EqualsAndHashCode(callSuper = true)
public class BIamUserLoginLogEntity extends BaseEntity {
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
     * 姓名
     */
    @TableField("real_name")
    private String realName;

    /**
     * 手机号
     */
    @TableField("phone")
    private String phone;

    /**
     * 操作
     */
    @TableField("auth_action")
    private BIamAuthActionEnum authAction;

    /**
     * 登录方式
     */
    @TableField("auth_method")
    private BIamAuthMethodEnum authMethod;

    /**
     * 结果
     */
    @TableField("auth_result")
    private BIamAuthResultEnum authResult;

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
     * 浏览器和操作系统等信息
     */
    @TableField("user_agent")
    private String userAgent;

    /**
     * access token id
     */
    @TableField("access_token_id")
    private String accessTokenId;

    /**
     * access token 过期时间
     */
    @TableField("access_token_expire")
    private Long accessTokenExpire;

    /**
     * refresh token id
     */
    @TableField("refresh_token_id")
    private String refreshTokenId;

    /**
     * refresh token 过期时间
     */
    @TableField("refresh_token_expire")
    private Long refreshTokenExpire;

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
