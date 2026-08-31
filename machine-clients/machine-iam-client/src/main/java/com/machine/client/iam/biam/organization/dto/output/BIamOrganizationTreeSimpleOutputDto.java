package com.machine.client.iam.biam.organization.dto.output;

import com.machine.sdk.base.envm.biam.organization.BIamOrganizationTypeEnum;
import com.machine.sdk.base.model.tree.TreeNode;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class BIamOrganizationTreeSimpleOutputDto extends TreeNode<BIamOrganizationTreeSimpleOutputDto> {

    private String code;

    @Schema(description = "组织类型(IamOrganizationTypeEnum)")
    private BIamOrganizationTypeEnum type;
}
