package com.machine.app.suprr.iam.organization.controller.vo.response;

import com.machine.sdk.common.model.tree.TreeNode;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Schema
@EqualsAndHashCode(callSuper = true)
public class SuperOrganizationTreeSimpleSelfResponseVo extends TreeNode<SuperOrganizationTreeSimpleSelfResponseVo> {

    @Schema(description = "编码")
    private String code;
}
