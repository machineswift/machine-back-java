package com.machine.app.suprr.iam.organization.controller.vo.response;

import com.machine.sdk.common.model.tree.TreeNode;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Schema
@EqualsAndHashCode(callSuper = true)
public class SuperOrganizationTreeExpandSelfResponseVo extends TreeNode<SuperOrganizationTreeExpandSelfResponseVo> {

    @Schema(description = "编码")
    private String code;

    @Schema(description = "门店数量")
    private Integer shopNumber;
}
