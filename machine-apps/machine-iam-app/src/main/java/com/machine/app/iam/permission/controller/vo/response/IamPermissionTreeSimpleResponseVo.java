package com.machine.app.iam.permission.controller.vo.response;

import com.machine.sdk.common.envm.iam.IamPermissionResourceTypeEnum;
import com.machine.sdk.common.model.tree.TreeNode;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Schema
@EqualsAndHashCode(callSuper = true)
public class IamPermissionTreeSimpleResponseVo extends TreeNode<IamPermissionTreeSimpleResponseVo> {

    @Schema(description = "资源类型（IamPermissionResourceTypeEnum）")
    private IamPermissionResourceTypeEnum resourceType;

    @Schema(description = "编码")
    private String code;

    @Schema(description = "名称")
    private String name;

    @Schema(description = "图标")
    private String icon;
}
