package com.machine.service.data.franchisee.dao.mapper.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.machine.starter.mybatis.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@TableName("t_data_franchisee_shop_relation")
public class DataFranchiseeShopRelationEntity extends BaseEntity {

    /**
     * 加盟商ID
     */
    @TableField("franchisee_id")
    private String franchiseeId;

    /**
     * 门店ID
     */
    @TableField("shop_id")
    private String shopId;

    /**
     * 排序
     */
    @TableField("sort")
    private Long sort;

}