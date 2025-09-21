package com.machine.service.iam.user.dao.mapper.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.machine.sdk.common.envm.iam.IamUserRoleBusinessTypeEnum;
import com.machine.starter.mybatis.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@TableName("t_iam_user_role_business_relation")
public class IamUserRoleBusinessRelationEntity extends BaseEntity {

    /**
     * 用户角色关系ID
     */
    @TableField("user_role_relation_id")
    private String userRoleRelationId;

    /**
     * 业务id
     */
    @TableField("business_id")
    private String businessId;

    /**
     * 类型
     */
    @TableField("business_type")
    private IamUserRoleBusinessTypeEnum businessType;

    /**
     * 排序
     */
    @TableField("sort")
    private Long sort;

}
