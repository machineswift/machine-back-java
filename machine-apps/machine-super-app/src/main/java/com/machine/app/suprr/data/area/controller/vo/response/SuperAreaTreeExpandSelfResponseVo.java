package com.machine.app.suprr.data.area.controller.vo.response;

import com.machine.sdk.common.model.tree.TreeNode;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Schema
@EqualsAndHashCode(callSuper = true)
public class SuperAreaTreeExpandSelfResponseVo extends TreeNode<SuperAreaTreeExpandSelfResponseVo> {

    @Schema(description = "门店数量")
    private Integer shopNumber;

}
