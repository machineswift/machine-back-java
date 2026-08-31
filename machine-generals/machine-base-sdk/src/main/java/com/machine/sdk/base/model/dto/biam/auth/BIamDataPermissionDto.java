package com.machine.sdk.base.model.dto.biam.auth;

import com.machine.sdk.base.envm.biam.permission.BIamDataPermissionResultTypeEnum;
import com.machine.sdk.base.envm.biam.organization.BIamOrganizationTypeEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;
import java.util.Set;

@Data
@Schema
@NoArgsConstructor
public class BIamDataPermissionDto {

    private BIamDataPermissionResultTypeEnum resultType;

    private Map<BIamOrganizationTypeEnum, Set<String>> organizationIdMap;

    private Set<String> shopIdSet;

    public BIamDataPermissionDto(BIamDataPermissionResultTypeEnum resultType) {
        this.resultType = resultType;
    }
}
