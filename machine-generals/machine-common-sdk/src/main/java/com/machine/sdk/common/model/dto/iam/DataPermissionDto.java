package com.machine.sdk.common.model.dto.iam;

import com.machine.sdk.common.envm.iam.IamDataPermissionResultTypeEnum;
import com.machine.sdk.common.envm.iam.organization.IamOrganizationTypeEnum;
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
