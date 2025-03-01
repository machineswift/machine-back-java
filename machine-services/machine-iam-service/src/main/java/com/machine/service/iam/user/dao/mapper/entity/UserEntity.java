package com.machine.service.iam.user.dao.mapper.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.machine.sdk.common.envm.StatusEnum;
import com.machine.sdk.common.envm.crm.customer.GenderEnum;
import com.machine.starter.mybatis.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@TableName("t_user")
@EqualsAndHashCode(callSuper = true)
public class UserEntity extends BaseEntity {
    /**
     * 系统账号(用户名)
     */
    @TableField("user_name")
    private String userName;

    /**
     * 状态
     * {@link StatusEnum}
     */
    @TableField("status")
    private StatusEnum status;

    /**
     * 密码，加密存储
     */
    @TableField("password")
    private String password;

    /**
     * 编码
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
     * 性别
     */
    @TableField("gender")
    private GenderEnum gender;

    /**
     * 描述
     */
    @TableField("description")
    private String description;
}
