package com.machine.app.iam.user.controller.vo.response;

import com.machine.sdk.common.envm.iam.role.RoleTypeEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.LinkedHashMap;
import java.util.List;

@Data
@Schema
public class IamUserRoleInfoResponse {

    @Schema(description = "角色ID")
    private String roleId;

    @Schema(description = "角色类型（RoleTypeEnum）")
    private RoleTypeEnum roleType;

    @Schema(description = "角色名称")
    private String roleName;

    @Schema(description = "角色编码")
    private String roleCode;

    @Schema(description = "组织集合")
    private List<TargetInfo> organizationList;

    @Schema(description = "门店集合")
    private List<TargetInfo> shopList;

    @Schema(description = "项目集合")
    private List<TargetInfo> projectList;

    @Schema(description = "公司集合")
    private List<TargetInfo> companytList;

    @Data
    @Schema
    public static class TargetInfo {

        @Schema(description = "id")
        private String id;

        @Schema(description = "名称")
        private String name;

        @Schema(description = "编码")
        private String code;

        @Schema(description = "排序")
        private Long sort;
    }
}
