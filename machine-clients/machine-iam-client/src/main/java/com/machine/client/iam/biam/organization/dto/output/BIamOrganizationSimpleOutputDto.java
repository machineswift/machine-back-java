package com.machine.client.iam.biam.organization.dto.output;

import com.machine.sdk.base.envm.biam.organization.BIamOrganizationTypeEnum;
import lombok.Data;

@Data
public class BIamOrganizationSimpleOutputDto {
    private String id;

    private String parentId;

    private String code;

    private String name;

    private BIamOrganizationTypeEnum type;

    /**
     * 排序
     */
    private Long sort;
}
