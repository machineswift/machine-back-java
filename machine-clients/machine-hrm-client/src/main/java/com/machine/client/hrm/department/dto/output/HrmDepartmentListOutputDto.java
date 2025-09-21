package com.machine.client.hrm.department.dto.output;

import lombok.Data;

@Data
public class HrmDepartmentListOutputDto {

    private String id;

    private String parentId;

    private String code;

    private String name;

    /**
     * 排序
     */
    private Long sort;

    /**
     * 创建人ID
     */
    private String createBy;

    /**
     * 创建时间（Unix 时间戳）
     */
    private Long createTime;

    /**
     * 操作人ID
     */
    private String updateBy;

    /**
     * 更新时间（Unix 时间戳）
     */
    private Long updateTime;

}
