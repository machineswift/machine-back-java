package com.machine.app.suprr.iam.organization.business.bo;

import com.machine.sdk.common.model.tree.TreeNode;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.HashSet;
import java.util.Set;

@Data
@Schema
@EqualsAndHashCode(callSuper = true)
public class SuperOrganizationTreeExpandBo extends TreeNode<SuperOrganizationTreeExpandBo> {

    @Schema(description = "编码")
    private String code;

    @Schema(description = "门店数量")
    private Integer shopNumber;

    private Set<String> shopIdSet = new HashSet<>();
}
