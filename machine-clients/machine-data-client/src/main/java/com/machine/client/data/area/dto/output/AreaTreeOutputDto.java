package com.machine.client.data.area.dto.output;

import com.machine.sdk.common.model.tree.TreeNode;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class AreaTreeOutputDto extends TreeNode<AreaTreeOutputDto> {

    /**
     * 编码
     */
    private String code;

}
