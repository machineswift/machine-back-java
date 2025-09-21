package com.machine.service.hrm.employee.dao.mapper.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.machine.sdk.beisen.envm.BeiSenEmployeeStatusEnum;
import com.machine.sdk.beisen.envm.BeiSenGenderEnum;
import com.machine.starter.mybatis.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@TableName("t_hrm_employee")
public class EmployeeEntity extends BaseEntity {

    /**
     * 用户id
     */
    @TableField("user_id")
    private String userId;

    /**
     * 用户名（系统账号）
     */
    @TableField("user_name")
    private String username;

    /**
     * 状态,（BeiSenEmployeeStatusEnum）
     */
    @TableField("status")
    private BeiSenEmployeeStatusEnum status;

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
     * 性别
     */
    @TableField("gender")
    private BeiSenGenderEnum gender;

    /**
     * 邮箱
     */
    @TableField("email")
    private String email;

    /**
     * 上级id
     */
    @TableField("leader_id")
    private String leaderId;

    /**
     * 职务ID
     */
    @TableField("job_post_id")
    private String jobPostId;

    /**
     * 部门ID
     */
    @TableField("department_id")
    private String departmentId;

    /**
     * 是否是部门负责人
     */
    @TableField("is_charge")
    private Boolean isCharge;

    /**
     * 描述
     */
    @TableField("description")
    private String description;
}