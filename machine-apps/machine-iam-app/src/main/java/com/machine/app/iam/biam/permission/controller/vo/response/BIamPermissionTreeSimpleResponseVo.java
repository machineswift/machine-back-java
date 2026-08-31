package com.machine.app.iam.biam.permission.controller.vo.response;

import com.machine.sdk.base.envm.biam.permission.BIamPermissionResourceTypeEnum;
import com.machine.sdk.base.model.tree.TreeNode;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Schema
@EqualsAndHashCode(callSuper = true)
public class BIamPermissionTreeSimpleResponseVo extends TreeNode<BIamPermissionTreeSimpleResponseVo> {

    @Schema(description = "资源类型（IamPermissionResourceTypeEnum）")
    private BIamPermissionResourceTypeEnum resourceType;

    @Schema(description = "编码")
    private String code;

    @Schema(description = "名称")
    private String name;

    @Schema(description = "图标")
    private String icon;
}
