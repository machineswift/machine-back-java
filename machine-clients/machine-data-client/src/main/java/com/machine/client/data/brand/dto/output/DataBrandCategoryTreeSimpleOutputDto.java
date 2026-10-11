package com.machine.client.data.brand.dto.output;

import com.machine.sdk.base.model.tree.TreeNode;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@Schema
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class DataBrandCategoryTreeSimpleOutputDto extends TreeNode<DataBrandCategoryTreeSimpleOutputDto> {

    @Schema(description = "编码")
    private String code;
}
