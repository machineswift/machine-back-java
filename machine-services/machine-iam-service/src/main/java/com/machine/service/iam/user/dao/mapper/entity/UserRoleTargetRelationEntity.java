package com.machine.service.iam.user.dao.mapper.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.machine.sdk.common.envm.iam.UserRoleTargetTypeEnum;
import com.machine.starter.mybatis.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@TableName("t_user_role_target_relation")
public class UserRoleTargetRelationEntity extends BaseEntity {
    /**
     * 用户ID
     */
    @TableField("user_id")
    private String userId;

    /**
     * 角色id
     */
    @TableField("role_id")
    private String roleId;

    /**
     * 目标id
     */
    @TableField("target_id")
    private String targetId;

    /**
     * 类型
     */
    @TableField("target_type")
    private UserRoleTargetTypeEnum targetType;

    /**
     * 排序
     */
    @TableField("sort")
    private Long sort;

}
