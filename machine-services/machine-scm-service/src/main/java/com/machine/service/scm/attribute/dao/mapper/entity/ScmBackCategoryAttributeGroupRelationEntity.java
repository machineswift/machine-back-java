package com.machine.service.scm.attribute.dao.mapper.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.machine.starter.mybatis.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@TableName("t_scm_back_category_attribute_group_relation")
@EqualsAndHashCode(callSuper = true)
public class ScmBackCategoryAttributeGroupRelationEntity extends BaseEntity {

    @TableField("group_id")
    private String groupId;

    @TableField("back_category_id")
    private String backCategoryId;

    @TableField("required_flag")
    private Boolean requiredFlag;

    @TableField("display_flag")
    private Boolean displayFlag;

    @TableField("searchable_flag")
    private Boolean searchableFlag;

    @TableField("sort")
    private Long sort;

    @TableField("features")
    private String features;
}
