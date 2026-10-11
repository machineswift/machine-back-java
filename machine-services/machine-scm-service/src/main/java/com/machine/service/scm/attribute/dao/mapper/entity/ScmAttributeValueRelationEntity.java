package com.machine.service.scm.attribute.dao.mapper.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.machine.starter.mybatis.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@TableName("t_scm_attribute_value_relation")
@EqualsAndHashCode(callSuper = true)
public class ScmAttributeValueRelationEntity extends BaseEntity {

    @TableField("parent_value_id")
    private String parentValueId;

    @TableField("child_attribute_id")
    private String childAttributeId;

    @TableField("sort")
    private Long sort;
}