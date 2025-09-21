package com.machine.client.iam.organization.dto.output;

import com.machine.sdk.common.envm.iam.organization.IamOrganizationTypeEnum;
import lombok.Data;

@Data
public class IamOrganizationSimpleOutputDto {
    private String id;

    private String parentId;

    private String code;

    private String name;

    private IamOrganizationTypeEnum type;

    /**
     * 排序
     */
    private Long sort;
}
