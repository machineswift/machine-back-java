package com.machine.app.openapi.iam.role.controller.vo.response;

import com.machine.sdk.common.envm.StatusEnum;
import com.machine.sdk.common.envm.iam.role.RoleTypeEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.Map;
import java.util.Set;

@Data
public class OpenApiRoleDetailResponseVo {

    @Schema(description = "角色ID")
    private String id;

    @Schema(description = "父ID")
    private String parentId;

    @Schema(description = "编码")
    private String code;

    @Schema(description = "类型（RoleTypeEnum）")
    private RoleTypeEnum type;

    @Schema(description = "状态（StatusEnum）")
    private StatusEnum status;

    @Schema(description = "名称")
    private String name;

    @Schema(description = "是否是默认角色")
    private Boolean defaultRole;

    @Schema(description = "排序")
    private Long sort;

    @Schema(description = "权限Id列表")
    private Set<String> permissionIdList;

    @Schema(description = "数据权限")
    private Map<String, String> dataPermissionMap;

}
