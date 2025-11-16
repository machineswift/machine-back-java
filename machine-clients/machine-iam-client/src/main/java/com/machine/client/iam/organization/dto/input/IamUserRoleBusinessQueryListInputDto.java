package com.machine.client.iam.organization.dto.input;

import com.machine.sdk.common.envm.iam.role.IamUserRoleBusinessTypeEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@NoArgsConstructor
public class IamUserRoleBusinessQueryListInputDto {

    @Schema(description = "类型（UserRoleBusinessTypeEnum）")
    private IamUserRoleBusinessTypeEnum businessType;

    @Schema(description = "用户角色关系Id集合")
    private Set<String> userRoleRelationIdSet;

    @Schema(description = "业务tId集合")
    private Set<String> businessIdSet;
}
