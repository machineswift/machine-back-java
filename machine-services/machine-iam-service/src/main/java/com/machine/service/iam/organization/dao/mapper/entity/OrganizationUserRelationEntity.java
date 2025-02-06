package com.machine.service.iam.organization.dao.mapper.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.machine.sdk.common.envm.iam.OrganizationTypeEnum;
import com.machine.starter.mybatis.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@TableName("t_organization_user_relation")
public class OrganizationUserRelationEntity extends BaseEntity {

    /**
     * 组织ID
     */
    @TableField("organization_id")
    private String organizationId;

    /**
     * 用户ID
     */
    @TableField("user_id")
    private String userId;

    /**
     * 排序
     */
    @TableField("sort")
    private Long sort;
}
