package com.machine.app.manage.hrm.department.controller.vo.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

@Data
public class HrmDepartmentDetailResponseVo {

    @Schema(description = "ID")
    private String id;

    @Schema(description = "父ID")
    private String parentId;

    @Schema(description = "编码")
    private String code;

    @Schema(description = "名称")
    private String name;

    @Schema(description = "部门机构中员工的人数")
    private Integer employeeNumber;

    @Schema(description = "部门负责人")
    private List<DepartmentChargeResponseVo> departmentChargeList;

    @Schema(description = "排序")
    private Long sort;

    @Schema(description = "设立日期（来源北森）")
    private Long establishDate;

    @Schema(description = "生效日期（来源北森）")
    private Long startDate;

    @Schema(description = "失效日期（来源北森）")
    private Long stopDate;

    @Schema(description = "行政维度上级")
    private ParentDepartment parentDepartment;

    @Schema(description = "层级")
    private Integer level;

    @Schema(description = "描述")
    private String description;

    @Schema(description = "创建时间（Unix 时间戳）")
    private Long createTime;

    @Schema(description = "更新时间（Unix 时间戳）")
    private Long updateTime;

    @Data
    @Schema
    public static class ParentDepartment {

        @Schema(description = "ID")
        private String id;

        @Schema(description = "编码")
        private String code;

        @Schema(description = "名称")
        private String name;

    }
}
