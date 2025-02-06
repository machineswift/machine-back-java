package com.machine.client.data.shop.dto.output;

import com.machine.sdk.common.envm.iam.OrganizationTypeEnum;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ShopOrganizationRelationListOutputDto {

    private String id;

    private String organizationId;

    private String shopId;

    private OrganizationTypeEnum organizationType;

    /**
     * 排序
     */
    private Long sort;

    /**
     * 创建人ID
     */
    private String createBy;


    /**
     * 创建时间（Unix 时间戳）
     */
    private Long createTime;

    /**
     * 操作人ID
     */
    private String updateBy;

    /**
     * 更新时间（Unix 时间戳）
     */
    private Long updateTime;
}


