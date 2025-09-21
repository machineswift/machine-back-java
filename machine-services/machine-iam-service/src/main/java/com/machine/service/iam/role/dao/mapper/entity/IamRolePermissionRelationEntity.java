package com.machine.service.iam.role.dao.mapper.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.machine.sdk.common.envm.iam.IamPermissionTypeEnum;
import com.machine.starter.mybatis.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@TableName("t_iam_role_permission_relation")
public class IamRolePermissionRelationEntity extends BaseEntity {
    /**
     * '角色ID'
     */
    @TableField("role_id")
    private String roleId;

    /**
     * 权限id
     */
    @TableField("permission_id")
    private String permissionId;

    /**
     * {@link IamPermissionTypeEnum}
     */
    @TableField("type")
    private IamPermissionTypeEnum type;

    /**
     * 数据权限
     */
    @TableField("data_permission_rules")
    private String dataPermissionRules;

    /**
     * 排序
     */
    @TableField("sort")
    private Long sort;
}
