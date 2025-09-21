package com.machine.service.iam.organization.dao.mapper.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.machine.sdk.common.envm.iam.organization.IamOrganizationTypeEnum;
import com.machine.starter.mybatis.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@TableName("t_iam_organization")
public class IamOrganizationEntity extends BaseEntity {

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
     * {@link IamOrganizationTypeEnum}
     */
    @TableField("type")
    private IamOrganizationTypeEnum type;

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
