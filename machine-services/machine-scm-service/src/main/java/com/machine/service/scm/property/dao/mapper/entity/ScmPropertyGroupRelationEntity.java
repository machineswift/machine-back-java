package com.machine.service.scm.property.dao.mapper.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.machine.starter.mybatis.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@TableName("t_scm_property_group_relation")
@EqualsAndHashCode(callSuper = true)
public class ScmPropertyGroupRelationEntity extends BaseEntity {

    @TableField("group_id")
    private String groupId;

    @TableField("property_id")
    private String propertyId;

    @TableField("sort")
    private Long sort;
}