package com.machine.client.hrm.department.dto.input;

import com.machine.sdk.common.envm.StatusEnum;
import lombok.Data;

@Data
public class HrmDepartmentCreateInputDto {

    private String id;
    /**
     * 父ID
     */
    private String parentId;

    /**
     * 名称
     */
    private String name;

    /**
     * 编码
     */
    private String code;

    /**
     * 状态
     */
    private StatusEnum status;

    /**
     * 描述
     */
    private String description;

    /**
     * 排序
     */
    private Long sort;
}
