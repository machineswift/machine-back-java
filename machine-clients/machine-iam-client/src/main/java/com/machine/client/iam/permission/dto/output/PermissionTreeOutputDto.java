package com.machine.client.iam.permission.dto.output;

import com.machine.sdk.common.envm.StatusEnum;
import com.machine.sdk.common.envm.iam.PermissionResourceTypeEnum;
import com.machine.sdk.common.model.tree.TreeNode;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class PermissionTreeOutputDto extends TreeNode<PermissionTreeOutputDto> {

    @Schema(description = "编码")
    private String code;

    @Schema(description = "状态（StatusEnum）")
    private StatusEnum status;

    @Schema(description = "资源类型（PermissionResourceTypeEnum）")
    private PermissionResourceTypeEnum resourceType;

    @Schema(description = "名称")
    private String name;

    @Schema(description = "路径")
    private String path;

    @Schema(description = "图标")
    private String iconUrl;

    @Schema(description = "排序")
    private Long sort;

    @Schema(description = "数据权限元数据（只有菜单才有值）")
    private String dataMetaInto;

    @Schema(description = "描述")
    private String description;
}
