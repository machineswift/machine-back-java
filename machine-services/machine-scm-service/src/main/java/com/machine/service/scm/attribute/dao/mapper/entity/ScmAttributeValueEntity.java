package com.machine.service.scm.attribute.dao.mapper.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.machine.sdk.base.envm.StatusEnum;
import com.machine.starter.mybatis.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@TableName("t_scm_attribute_value")
@EqualsAndHashCode(callSuper = true)
public class ScmAttributeValueEntity extends BaseEntity {

    @TableField("attribute_id")
    private String attributeId;

    @TableField("code")
    private String code;

    @TableField("name")
    private String name;

    @TableField("value")
    private String value;

    @TableField("sort")
    private Long sort;

    @TableField("status")
    private StatusEnum status;

    @TableField("features")
    private String features;
}