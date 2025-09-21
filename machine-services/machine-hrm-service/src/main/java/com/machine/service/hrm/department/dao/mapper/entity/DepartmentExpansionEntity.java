package com.machine.service.hrm.department.dao.mapper.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.machine.starter.mybatis.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@TableName("t_hrm_department_expansion")
public class DepartmentExpansionEntity extends BaseEntity {

    /**
     * 部门ID
     */
    @TableField("department_id")
    private String departmentId;

    /**
     * 部门负责人ID
     */
    @TableField("person_in_charge")
    private String personInCharge;


    /**
     * 设立日期
     */
    @TableField("establish_date")
    private Long establishDate;


    /**
     * 生效日期
     */
    @TableField("start_date")
    private Long starDate;


    /**
     * 失效日期
     */
    @TableField("stop_time")
    private Long stopTime;

}

