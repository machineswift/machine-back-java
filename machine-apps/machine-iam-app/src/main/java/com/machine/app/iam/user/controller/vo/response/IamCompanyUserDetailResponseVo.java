package com.machine.app.iam.user.controller.vo.response;

import com.machine.sdk.common.envm.StatusEnum;
import com.machine.sdk.common.envm.data.CompanyEmployeeStatusEnum;
import com.machine.sdk.common.envm.iam.UserTypeEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

@Data
@Schema
public class IamCompanyUserDetailResponseVo {

    @Schema(description = "ID")
    private String id;

    @Schema(description = "状态（StatusEnum）")
    private StatusEnum status;

    @Schema(description = "类型（UserTypeEnum）")
    private List<UserTypeEnum> userTypeList;

    @Schema(description = "系统账号(用户名)")
    private String userName;

    @Schema(description = "编码（工号）")
    private String code;

    @Schema(description = "名称")
    private String name;

    @Schema(description = "手机号")
    private String phone;

    @Schema(description = "公司员工ID")
    private String companyEmployeeId;

    @Schema(description = "员工状态（CompanyEmployeeStatusEnum）")
    private CompanyEmployeeStatusEnum employeeStatus;

    @Schema(description = "职务名称列表")
    private List<String> jobPostNameList;

    @Schema(description = "部门名称集合")
    private List<String> departmentNameList;

    @Schema(description = "上级姓名集合")
    private List<String> leaderNameList;

    @Schema(description = "描述")
    private String description;

    @Schema(description = "用户角色信息")
    private List<IamUserRoleInfoResponse> userRoleList;

    @Schema(description = "创建人姓名")
    private String createName;

    @Schema(description = "创建人ID")
    private String createBy;

    @Schema(description = "创建时间（Unix 时间戳）")
    private Long createTime;

    @Schema(description = "操作人姓名")
    private String updateName;

    @Schema(description = "操作人ID")
    private String updateBy;

    @Schema(description = "更新时间（Unix 时间戳）")
    private Long updateTime;

}
