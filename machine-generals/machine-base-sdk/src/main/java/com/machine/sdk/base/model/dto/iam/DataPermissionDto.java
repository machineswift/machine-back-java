package com.machine.sdk.base.model.dto.iam;

import com.machine.sdk.base.envm.iam.permission.IamDataPermissionResultTypeEnum;
import com.machine.sdk.base.envm.iam.organization.IamOrganizationTypeEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;
import java.util.Set;

@Data
@Schema
@NoArgsConstructor
public class DataPermissionDto {

    private IamDataPermissionResultTypeEnum resultType;

    private Map<IamOrganizationTypeEnum, Set<String>> organizationIdMap;

    private Set<String> shopIdSet;

    public DataPermissionDto(IamDataPermissionResultTypeEnum resultType) {
        this.resultType = resultType;
    }
}
