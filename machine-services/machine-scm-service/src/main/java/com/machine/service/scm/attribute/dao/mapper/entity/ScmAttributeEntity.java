package com.machine.service.scm.attribute.dao.mapper.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.machine.sdk.base.envm.scm.category.ScmAttributeInputTypeEnum;
import com.machine.sdk.base.envm.scm.category.ScmAttributeTypeEnum;
import com.machine.starter.mybatis.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@TableName("t_scm_attribute")
@EqualsAndHashCode(callSuper = true)
public class ScmAttributeEntity extends BaseEntity {

    @TableField("code")
    private String code;

    @TableField("name")
    private String name;

    @TableField("attribute_type")
    private ScmAttributeTypeEnum attributeType;

    @TableField("input_type")
    private ScmAttributeInputTypeEnum inputType;

    @TableField("required_flag")
    private Boolean requiredFlag;

    @TableField("multiple_flag")
    private Boolean multipleFlag;

    @TableField("searchable_flag")
    private Boolean searchableFlag;

    @TableField("features")
    private String features;
}