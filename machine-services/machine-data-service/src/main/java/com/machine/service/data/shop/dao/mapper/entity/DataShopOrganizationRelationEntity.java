package com.machine.service.data.shop.dao.mapper.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.machine.sdk.common.envm.iam.organization.IamOrganizationTypeEnum;
import com.machine.starter.mybatis.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@TableName("t_data_shop_organization_relation")
public class DataShopOrganizationRelationEntity extends BaseEntity {

    /**
     * 门店id
     */
    @TableField("shop_id")
    private String shopId;

    /**
     * 组织id
     */
    @TableField("organization_id")
    private String organizationId;

    /**
     * {@link IamOrganizationTypeEnum}
     */
    @TableField("organization_type")
    private IamOrganizationTypeEnum organizationType;

    /**
     * 排序
     */
    @TableField("sort")
    private Long sort;
}
