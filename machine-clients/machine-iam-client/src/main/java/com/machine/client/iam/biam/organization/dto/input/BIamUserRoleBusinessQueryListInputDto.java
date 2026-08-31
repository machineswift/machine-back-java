package com.machine.client.iam.biam.organization.dto.input;

import com.machine.sdk.base.envm.biam.role.BIamUserRoleBusinessTypeEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@NoArgsConstructor
public class BIamUserRoleBusinessQueryListInputDto {

    @Schema(description = "类型（UserRoleBusinessTypeEnum）")
    private BIamUserRoleBusinessTypeEnum businessType;

    @Schema(description = "用户角色关系Id集合")
    private Set<String> userRoleRelationIdSet;

    @Schema(description = "业务tId集合")
    private Set<String> businessIdSet;
}
