package com.machine.service.scm.category.dao.mapper.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.machine.starter.mybatis.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@TableName("t_scm_back_category")
@EqualsAndHashCode(callSuper = true)
public class ScmBackCategoryEntity extends BaseEntity {

    @TableField("parent_id")
    private String parentId;

    @TableField("code")
    private String code;

    @TableField("name")
    private String name;

    @TableField("sort")
    private Long sort;

    @TableField("features")
    private String features;
}