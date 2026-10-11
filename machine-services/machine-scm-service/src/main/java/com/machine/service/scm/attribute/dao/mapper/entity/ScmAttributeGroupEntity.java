package com.machine.service.scm.attribute.dao.mapper.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.machine.sdk.base.envm.StatusEnum;
import com.machine.sdk.base.envm.scm.category.ScmAttributeGroupTypeEnum;
import com.machine.starter.mybatis.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@TableName("t_scm_attribute_group")
@EqualsAndHashCode(callSuper = true)
public class ScmAttributeGroupEntity extends BaseEntity {

    @TableField("code")
    private String code;

    @TableField("name")
    private String name;

    @TableField("group_type")
    private ScmAttributeGroupTypeEnum groupType;

    @TableField("status")
    private StatusEnum status;

    @TableField("features")
    private String features;
}