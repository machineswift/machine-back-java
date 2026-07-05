package com.machine.service.scm.property.dao.mapper.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.machine.starter.mybatis.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@TableName("t_scm_property_group")
@EqualsAndHashCode(callSuper = true)
public class ScmPropertyGroupEntity extends BaseEntity {

    @TableField("back_category_id")
    private String backCategoryId;

    @TableField("name")
    private String name;

    @TableField("sort")
    private Long sort;
}