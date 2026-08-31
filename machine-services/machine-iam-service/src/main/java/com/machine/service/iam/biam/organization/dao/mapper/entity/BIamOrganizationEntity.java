package com.machine.service.iam.biam.organization.dao.mapper.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.machine.sdk.base.envm.biam.organization.BIamOrganizationTypeEnum;
import com.machine.starter.mybatis.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@TableName("t_biam_organization")
public class BIamOrganizationEntity extends BaseEntity {

    /**
     * 父ID
     */
    @TableField("parent_id")
    private String parentId;

    /**
     * 名称
     */
    @TableField("name")
    private String name;

    /**
     * 编码
     */
    @TableField("code")
    private String code;

    /**
     * {@link BIamOrganizationTypeEnum}
     */
    @TableField("type")
    private BIamOrganizationTypeEnum type;

    /**
     * 描述
     */
    @TableField("description")
    private String description;


    /**
     * 排序
     */
    @TableField("sort")
    private Long sort;
}
