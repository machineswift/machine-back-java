package com.machine.service.data.brand.dao.mapper.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.machine.starter.mybatis.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@TableName("t_data_brand_category_relation")
@EqualsAndHashCode(callSuper = true)
public class DataBrandCategoryRelationEntity extends BaseEntity {

    @TableField("brand_id")
    private String brandId;

    @TableField("category_id")
    private String categoryId;

    @TableField("sort")
    private Long sort;
}
