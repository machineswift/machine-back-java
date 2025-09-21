package com.machine.service.data.shop.dao.mapper.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.machine.starter.mybatis.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@TableName("t_data_shop_label_option_relation")
@EqualsAndHashCode(callSuper = true)
public class DataShopLabelOptionRelationEntity extends BaseEntity {

    /**
     * 门店ID
     */
    @TableField("shop_id")
    private String shopId;

    /**
     * 人工标签选项id
     */
    @TableField("label_option_id")
    private String labelOptionId;


    /**
     * 排序
     */
    @TableField("sort")
    private Long sort;
}
