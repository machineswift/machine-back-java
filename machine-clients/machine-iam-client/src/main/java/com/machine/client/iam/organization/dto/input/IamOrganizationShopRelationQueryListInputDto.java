package com.machine.client.iam.organization.dto.input;

import com.machine.sdk.common.envm.iam.organization.IamOrganizationTypeEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@Schema
@NoArgsConstructor
public class IamOrganizationShopRelationQueryListInputDto {

    @Schema(description = "组织类型")
    private IamOrganizationTypeEnum organizationType;

    @Schema(description = "组织Id集合")
    private Set<String> organizationIdSet;

    @Schema(description = "门店Id集合")
    private Set<String> shopIdSet;

}
