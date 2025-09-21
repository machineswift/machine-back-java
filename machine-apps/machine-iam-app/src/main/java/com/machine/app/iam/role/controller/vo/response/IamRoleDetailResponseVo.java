package com.machine.app.iam.role.controller.vo.response;

import com.machine.sdk.common.envm.StatusEnum;
import com.machine.sdk.common.envm.iam.role.IamRoleTypeEnum;
import com.machine.sdk.common.model.dto.iam.DataPermissionRuleDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;
import java.util.Set;

@Data
@Schema
@NoArgsConstructor
public class IamRoleDetailResponseVo {

    @Schema(description = "ID")
    private String id;

    @Schema(description = "类型（IamRoleTypeEnum）")
    private IamRoleTypeEnum type;

    @Schema(description = "编码")
    private String code;

    @Schema(description = "名称")
    private String name;

    @Schema(description = "状态（StatusEnum）")
    private StatusEnum status;

    @Schema(description = "是否是默认角色")
    private Boolean defaultRole;

    @Schema(description = "描述")
    private String description;

    @Schema(description = "数据权限")
    private DataPermissionRuleDto dataPermissionRule;

    @Schema(description = "权限Id列表")
    private Set<String> permissionIdSet;

    @Schema(description = "菜单数据权限")
    private Map<String, List<DataPermissionRuleDto>> dataPermissionRuleMap;

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
