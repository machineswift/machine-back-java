package com.machine.service.data.employee.dao.mapper.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.machine.sdk.common.envm.hrm.HrmEmployeeStatusEnum;
import com.machine.starter.mybatis.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@TableName("t_data_company_employee")
public class DataCompanyEmployeeEntity extends BaseEntity {
    /**
     * 用户Id
     */
    @TableField("user_id")
    private String userId;

    /**
     * 公司员工状态
     */
    @TableField("employee_status")
    private HrmEmployeeStatusEnum employeeStatus;
}