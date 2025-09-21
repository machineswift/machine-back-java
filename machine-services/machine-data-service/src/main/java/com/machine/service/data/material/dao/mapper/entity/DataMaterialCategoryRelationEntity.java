package com.machine.service.data.material.dao.mapper.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.machine.starter.mybatis.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@TableName("t_data_material_category_relation")
@EqualsAndHashCode(callSuper = true)
public class DataMaterialCategoryRelationEntity extends BaseEntity {
    /**
     * 分类id
     */
    @TableField("category_id")
    private String categoryId;

    /**
     * 素材ID
     */
    @TableField("material_id")
    private String materialId;

    /**
     * 排序
     */
    @TableField("sort")
    private Long sort;
}
