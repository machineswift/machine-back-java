package com.machine.service.data.shop.dao.mapper.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.machine.starter.mybatis.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@TableName("t_data_user_collect_shop")
@EqualsAndHashCode(callSuper = true)
public class DataUserCollectShopEntity extends BaseEntity {

    /**
     * 门店Id
     */
    @TableField("user_id")
    private String userId;

    /**
     * 门店Id
     */
    @TableField("shop_id")
    private String shopId;


    /**
     * 排序
     */
    private Long sort;
}
