package com.machine.app.manage.hrm.department.business.bo;

import com.machine.app.manage.hrm.department.controller.vo.response.DepartmentChargeResponseVo;
import com.machine.sdk.common.model.tree.TreeNode;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@Schema
@EqualsAndHashCode(callSuper = true)
public class HrmDepartmentExpandTreeBo extends TreeNode<HrmDepartmentExpandTreeBo> {

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
    private int departmentNumber;

    private Set<String> employeeIdSet = new HashSet<>();

    private Set<String> departmentIdSet = new HashSet<>();

    @Schema(description = "创建时间（Unix 时间戳）")
    private Long createTime;

    @Schema(description = "更新时间（Unix 时间戳）")
    private Long updateTime;
}
