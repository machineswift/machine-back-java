package com.machine.service.data.employee.dao.mapper.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.machine.sdk.common.envm.data.DataShopEmployeeStatusEnum;
import com.machine.starter.mybatis.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@TableName("t_data_shop_employee")
public class DataShopEmployeeEntity extends BaseEntity {
    /**
     * 用户Id
     */
    @TableField("user_id")
    private String userId;

    /**
     * 门店员工状态
     */
    @TableField("employee_status")
    private DataShopEmployeeStatusEnum employeeStatus;
}