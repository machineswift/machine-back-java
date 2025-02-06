package com.machine.sdk.common.model.dto.iam;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@NoArgsConstructor
public class DataPermissionDto {

    private Set<String> organizationIdSet;

    private Set<String> shopIdSet;
}
