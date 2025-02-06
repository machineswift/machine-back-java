package com.machine.app.manage.hrm.department.controller.vo.response;

import com.machine.sdk.common.model.tree.TreeNode;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
@Schema
@EqualsAndHashCode(callSuper = true)
public class HrmDepartmentExpandTreeResponseVo extends TreeNode<HrmDepartmentExpandTreeResponseVo> {

    @Schema(description = "编码")
    private String code;

    @Schema(description = "部门负责人")
    private List<DepartmentChargeResponseVo> departmentChargeList;

    @Schema(description = "描述")
    private String description;

    @Schema(description = "设立日期（Unix 时间戳）")
    private Long establishDate;

    @Schema(description = "生效日期（Unix 时间戳）")
    private Long startDate;

    @Schema(description = "失效日期（Unix 时间戳）")
    private Long stopDate;

    @Schema(description = "部门机构中的员工数")
    private Integer employeeNumber;

    @Schema(description = "部门机构中的部门数")
    private Integer departmentNumber;

    @Schema(description = "创建时间（Unix 时间戳）")
    private Long createTime;

    @Schema(description = "更新时间（Unix 时间戳）")
    private Long updateTime;
}
