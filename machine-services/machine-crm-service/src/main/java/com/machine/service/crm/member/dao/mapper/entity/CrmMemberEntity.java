package com.machine.service.crm.member.dao.mapper.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.machine.sdk.common.envm.base.GenderEnum;
import com.machine.starter.mybatis.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@TableName("t_crm_member")
@EqualsAndHashCode(callSuper = true)
public class CrmMemberEntity extends BaseEntity {

    /**
     * 编码
     */
    @TableField("code")
    private String code;

    /**
     * 邮箱
     */
    @TableField("email")
    private String email;

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
     * 生日年
     */
    @TableField("birth_year")
    private Integer birthYear;

    /**
     * 生日月
     */
    @TableField("birth_month")
    private Integer birthMonth;

    /**
     * 生日天
     */
    @TableField("birth_day")
    private Integer birthDay;

}