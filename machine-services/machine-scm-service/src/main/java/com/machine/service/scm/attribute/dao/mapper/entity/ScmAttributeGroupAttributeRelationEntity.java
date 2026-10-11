package com.machine.service.scm.attribute.dao.mapper.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.machine.starter.mybatis.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@TableName("t_scm_attribute_group_attribute_relation")
@EqualsAndHashCode(callSuper = true)
public class ScmAttributeGroupAttributeRelationEntity extends BaseEntity {

    @TableField("group_id")
    private String groupId;

    @TableField("attribute_id")
    private String attributeId;

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