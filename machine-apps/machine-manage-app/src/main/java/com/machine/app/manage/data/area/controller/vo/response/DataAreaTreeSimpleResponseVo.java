package com.machine.app.manage.data.area.controller.vo.response;

import com.machine.sdk.common.model.tree.TreeNode;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Schema
@EqualsAndHashCode(callSuper = true)
public class DataAreaTreeSimpleResponseVo extends TreeNode<DataAreaTreeSimpleResponseVo> {

    @Schema(description = "编码")
    private String code;
}
