package com.machine.service.scm.property.dao.mapper.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.machine.sdk.base.envm.scm.category.ScmItemInputTypeEnum;
import com.machine.sdk.base.envm.scm.category.ScmProperityTypeEnum;
import com.machine.starter.mybatis.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@TableName("t_scm_property")
@EqualsAndHashCode(callSuper = true)
public class ScmPropertyEntity extends BaseEntity {

    @TableField("code")
    private String code;

    @TableField("name")
    private String name;

    @TableField("property_type")
    private ScmProperityTypeEnum propertyType;

    @TableField("input_type")
    private ScmItemInputTypeEnum inputType;

    @TableField("is_required")
    private Boolean isRequired;

    @TableField("is_multiple")
    private Boolean isMultiple;

    @TableField("is_search")
    private Boolean isSearch;

    @TableField("features")
    private String features;
}