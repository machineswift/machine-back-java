package com.machine.service.scm.property.dao.mapper.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.machine.starter.mybatis.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@TableName("t_scm_property_value_relation")
@EqualsAndHashCode(callSuper = true)
public class ScmPropertyValueRelationEntity extends BaseEntity {

    @TableField("parent_value_id")
    private String parentValueId;

    @TableField("child_property_id")
    private String childPropertyId;

    @TableField("sort")
    private Long sort;
}