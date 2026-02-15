package com.machine.client.hrm.employee.dto.output;

import com.machine.sdk.common.envm.base.GenderEnum;
import com.machine.sdk.common.envm.hrm.HrmEmployeeStatusEnum;
import lombok.Data;

@Data
public class HrmEmployeeListOutputDto {

    private String  id;

    /**
     * 用户id
     */
    private String userId;

    /**
     * 用户名（系统账号）
     */
    private String username;

    /**
     * 状态,（HrmEmployeeStatusEnum）
     */
    private HrmEmployeeStatusEnum status;

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
     * 性别
     */
    private GenderEnum gender;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 上级id
     */
    private String leaderId;

    /**
     * 职务ID
     */
    private String jobPostId;

    /**
     * 部门ID
     */
    private String departmentId;

    /**
     * 是否是部门负责人
     */
    private Boolean isCharge;

    /**
     * 描述
     */
    private String description;
}
