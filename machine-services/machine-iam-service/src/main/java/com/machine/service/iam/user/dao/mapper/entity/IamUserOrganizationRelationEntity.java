package com.machine.service.iam.user.dao.mapper.entity;

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
@TableName("t_iam_user_organization_relation")
public class IamUserOrganizationRelationEntity extends BaseEntity {

    /**
     * 用户ID
     */
    @TableField("user_id")
    private String userId;

    /**
     * 组织id
     */
    @TableField("organization_id")
    private String organizationId;

    /**
     * 组织类型
     */
    @TableField("organization_type")
    private IamOrganizationTypeEnum organizationType;

    /**
     * 排序
     */
    @TableField("sort")
    private Long sort;

}
