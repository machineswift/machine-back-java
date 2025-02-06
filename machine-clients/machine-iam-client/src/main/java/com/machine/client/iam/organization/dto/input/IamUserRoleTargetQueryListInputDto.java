package com.machine.client.iam.organization.dto.input;

import com.machine.sdk.common.envm.iam.UserRoleTargetTypeEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@NoArgsConstructor
public class IamUserRoleTargetQueryListInputDto {

    @Schema(description = "类型（UserRoleTargetTypeEnum）")
    private UserRoleTargetTypeEnum targetType;

    @Schema(description = "用户Id集合")
    private Set<String> userIdSet;

    @Schema(description = "角色Id集合")
    private Set<String> roleIdSet;

    @Schema(description = "targetId集合")
    private Set<String> targetIdSet;
}
