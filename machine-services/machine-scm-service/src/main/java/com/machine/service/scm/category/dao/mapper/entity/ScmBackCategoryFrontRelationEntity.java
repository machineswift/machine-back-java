package com.machine.service.scm.category.dao.mapper.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.machine.starter.mybatis.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@TableName("t_scm_back_category_front_relation")
@EqualsAndHashCode(callSuper = true)
public class ScmBackCategoryFrontRelationEntity extends BaseEntity {

    @TableField("front_category_id")
    private String frontCategoryId;

    @TableField("back_category_id")
    private String backCategoryId;

    @TableField("sort")
    private Long sort;
}