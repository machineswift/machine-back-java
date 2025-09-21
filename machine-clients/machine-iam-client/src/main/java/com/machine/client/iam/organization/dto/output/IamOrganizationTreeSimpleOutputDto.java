package com.machine.client.iam.organization.dto.output;

import com.machine.sdk.common.envm.iam.organization.IamOrganizationTypeEnum;
import com.machine.sdk.common.model.tree.TreeNode;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class IamOrganizationTreeSimpleOutputDto extends TreeNode<IamOrganizationTreeSimpleOutputDto> {

    private String code;

    @Schema(description = "组织类型(IamOrganizationTypeEnum)")
    private IamOrganizationTypeEnum type;
}
