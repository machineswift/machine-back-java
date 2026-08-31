package com.machine.client.iam.biam.organization.dto.input;

import com.machine.sdk.base.envm.biam.organization.BIamOrganizationTypeEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@Schema
@NoArgsConstructor
public class BIamOrganizationShopRelationQueryListInputDto {

    @Schema(description = "组织类型")
    private BIamOrganizationTypeEnum organizationType;

    @Schema(description = "组织Id集合")
    private Set<String> organizationIdSet;

    @Schema(description = "门店Id集合")
    private Set<String> shopIdSet;

}
