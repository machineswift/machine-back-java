package com.machine.sdk.common.model.dto.iam;

import com.machine.sdk.common.envm.iam.DataPermissionScopeTypeEnum;
import com.machine.sdk.common.envm.iam.organization.OrganizationSelectTypeEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@Schema
public class DataPermissionRuleDto {

    @Schema(description = "模块编码")
    @NotBlank(message = "模块编码不能为空")
    private String moduleCode;

    @Schema(description = "类型（DataPermissionScopeTypeEnum）")
    @NotBlank(message = "类型不能为空")
    private DataPermissionScopeTypeEnum scopeType;

    @Schema(description = "组织节点信息")
    private List<OrganizationNode> organizationNodeList;

    @Data
    @NoArgsConstructor
    @Schema
    public static class OrganizationNode {

        @Schema(description = "选择类型（OrganizationSelectTypeEnum）")
        @NotBlank(message = "选择类型不能为空")
        private OrganizationSelectTypeEnum selectType;

        @Schema(description = "组织ID")
        @NotBlank(message = "组织ID不能为空")
        private String organizationId;

    }

}
