package com.machine.sdk.base.model.dto.biam.auth;

import com.machine.sdk.base.envm.biam.organization.BIamOrganizationSelectTypeEnum;
import com.machine.sdk.base.envm.biam.organization.BIamOrganizationTypeEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.validation.annotation.Validated;

import java.util.Map;
import java.util.Set;

@Data
@Schema
@Validated
@NoArgsConstructor
public class BIamDataPermissionRuleDto {

    @Schema(description = "模块编码")
    @NotBlank(message = "模块编码不能为空")
    private String functionCode;

    @Schema(description = "范围编码")
    @NotBlank(message = "范围编码不能为空")
    private String scopeCode;

    @Schema(description = "组织节点信息")
    private Map<BIamOrganizationTypeEnum, OrganizationNode> organizationNodeMap;

    @Data
    @NoArgsConstructor
    @Schema
    public static class OrganizationNode {

        @Schema(description = "选择类型（OrganizationSelectTypeEnum）")
        @NotBlank(message = "选择类型不能为空")
        private BIamOrganizationSelectTypeEnum selectType;

        @Schema(description = "组织ID集合")
        @NotEmpty(message = "组织I集合D不能为空")
        private Set<String> organizationIdSet;

    }

}
