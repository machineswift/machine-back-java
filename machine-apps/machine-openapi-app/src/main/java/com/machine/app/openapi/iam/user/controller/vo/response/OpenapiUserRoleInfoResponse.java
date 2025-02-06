package com.machine.app.openapi.iam.user.controller.vo.response;

import com.machine.sdk.common.envm.iam.role.RoleTypeEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

@Data
@Schema
public class OpenapiUserRoleInfoResponse {

    @Schema(description = "角色ID")
    private String roleId;

    @Schema(description = "角色编码")
    private String roleCode;

    @Schema(description = "角色名称")
    private String roleName;

    @Schema(description = "角色类型（RoleTypeEnum）")
    private RoleTypeEnum roleType;

    @Schema(description = "门店ID集合")
    private List<String> shopIdList;

    @Schema(description = "组织ID集合")
    private List<String> organizationIdList;

    @Schema(description = "项目ID集合")
    private List<String> projectIdList;

    @Schema(description = "公司ID集合")
    private List<String> companyIdList;

}
