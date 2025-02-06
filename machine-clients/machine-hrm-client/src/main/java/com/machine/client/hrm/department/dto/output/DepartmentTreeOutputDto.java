package com.machine.client.hrm.department.dto.output;

import com.machine.sdk.common.model.tree.TreeNode;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class DepartmentTreeOutputDto extends TreeNode<DepartmentTreeOutputDto> {
    private String code;
}
