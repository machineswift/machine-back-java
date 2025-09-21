package com.machine.client.hrm.department.dto.output;

import lombok.Data;

@Data
public class HrmDepartmentExpansionListOutputDto {
    /**
     * 部门ID
     */
    private String departmentId;

    /**
     * 部门负责人ID
     */
    private String personInCharge;


    /**
     * 设立日期
     */
    private Long establishDate;


    /**
     * 生效日期
     */
    private Long starDate;


    /**
     * 失效日期
     */
    private Long stopTime;
}
