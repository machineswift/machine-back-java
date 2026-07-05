package com.machine.client.scm.category.dto.output;

import com.machine.sdk.base.model.tree.TreeNode;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class ScmBackCategoryTreeSimpleOutputDto extends TreeNode<ScmBackCategoryTreeSimpleOutputDto> {

    @Schema(description = "编码")
    private String code;
}
