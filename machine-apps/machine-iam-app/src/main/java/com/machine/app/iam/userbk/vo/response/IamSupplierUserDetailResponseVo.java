package com.machine.app.iam.userbk.vo.response;

import com.machine.app.iam.user.controller.vo.response.IamUserRoleInfoResponse;
import com.machine.sdk.common.envm.StatusEnum;
import com.machine.sdk.common.envm.crm.customer.CrmGenderEnum;
import com.machine.sdk.common.envm.iam.IamUserTypeEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

@Data
@Schema
public class IamSupplierUserDetailResponseVo {

    @Schema(description = "id")
    private String id;

    @Schema(description = "供应商Id")
    private String supplierId;

    @Schema(description = "系统账号(用户名)")
    private String username;

    @Schema(description = "编码（工号）")
    private String code;

    @Schema(description = "状态（StatusEnum）")
    private StatusEnum status;

    @Schema(description = "类型（UserTypeEnum）")
    private List<IamUserTypeEnum> userTypeList;

    @Schema(description = "名称")
    private String name;

    @Schema(description = "手机号")
    private String phone;

    @Schema(description = "性别(GenderEnum)")
    private CrmGenderEnum gender;

    @Schema(description = "归属公司集合")
    private List<Company> companyList;

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

    @Data
    public static class Company {
        @Schema(description = "Id")
        private String id;

        @Schema(description = "名称")
        private String name;
    }
}
