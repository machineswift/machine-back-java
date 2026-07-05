package com.machine.service.scm.property.dao.mapper.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.machine.starter.mybatis.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@TableName("t_scm_property_value")
@EqualsAndHashCode(callSuper = true)
public class ScmPropertyValueEntity extends BaseEntity {

    @TableField("property_id")
    private String propertyId;

    @TableField("value")
    private String value;

    @TableField("sort")
    private Long sort;
}