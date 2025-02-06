package com.machine.client.hrm.department.dto.input;

import com.machine.sdk.beisen.envm.BeiSenDepartmentStatusEnum;
import lombok.Data;

@Data
public class DepartmentCreateInputDto {

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
    private BeiSenDepartmentStatusEnum status;

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

    /**
     * 描述
     */
    private String description;

    /**
     * 排序
     */
    private Long sort;
}
