package com.machine.client.data.tag.dto.output;

import com.machine.sdk.common.envm.data.tag.ProfileSubjectTypeEnum;
import com.machine.sdk.common.model.tree.TreeNode;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class DataTagCategoryTreeSimpleOutputDto extends TreeNode<DataTagCategoryTreeSimpleOutputDto> {

    @Schema(description = "编码")
    private String code;

    @Schema(description = "画像主体类型(ProfileSubjectTypeEnum)")
    private ProfileSubjectTypeEnum type;
}
