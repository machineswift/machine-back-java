package com.machine.client.hrm.department.dto.output;

import lombok.Data;

@Data
public class HrmDepartmentSimpleOutputDto {

    private String id;

    private String parentId;

    private String code;

    private String name;


    /**
     * 排序
     */
    private Long sort;
}
