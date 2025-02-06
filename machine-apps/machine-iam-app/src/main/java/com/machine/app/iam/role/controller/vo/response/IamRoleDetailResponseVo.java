package com.machine.app.iam.role.controller.vo.response;

import com.machine.sdk.common.envm.StatusEnum;
import com.machine.sdk.common.envm.iam.role.RoleTypeEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;
import java.util.Map;
import java.util.Set;

@Data
public class IamRoleDetailResponseVo {

    @Schema(description = "ID")
    private String id;

    @Schema(description = "编码")
    private String code;

    @Schema(description = "类型（RoleTypeEnum）")
    private RoleTypeEnum type;

    @Schema(description = "名称")
    private String name;

    @Schema(description = "状态（StatusEnum）")
    private StatusEnum status;

    @Schema(description = "是否是默认角色")
    private Boolean defaultRole;

    @Schema(description = "职务列表")
    private List<IamJobPostListResponseVo> jobPostList;

    @Schema(description = "描述")
    private String description;

    @Schema(description = "权限Id列表")
    private Set<String> permissionIdList;

    @Schema(description = "数据权限")
    private Map<String, String> dataPermissionMap;

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
