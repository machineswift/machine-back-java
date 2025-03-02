package com.machine.sdk.common.model.dto.iam;

import com.machine.sdk.common.envm.iam.DataPermissionResultTypeEnum;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@NoArgsConstructor
public class DataPermissionDto {

    private DataPermissionResultTypeEnum resultType;

    private Set<String> organizationIdSet;

    private Set<String> shopIdSet;

    private Set<String> projectIdSet;

    public DataPermissionDto(DataPermissionResultTypeEnum resultType) {
        this.resultType = resultType;
    }
}
