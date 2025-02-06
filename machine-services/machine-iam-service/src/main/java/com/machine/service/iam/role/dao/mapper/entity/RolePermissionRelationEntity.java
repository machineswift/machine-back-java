package com.machine.service.iam.role.dao.mapper.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.machine.sdk.common.envm.iam.PermissionTypeEnum;
import com.machine.starter.mybatis.BaseEntity;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@TableName("t_role_permission_relation")
public class RolePermissionRelationEntity extends BaseEntity {
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
     * {@link PermissionTypeEnum}
     */
    @TableField("type")
    private PermissionTypeEnum type;

    /**
     * 数据权限
     */
    @TableField("data_into")
    private String dataInto;

    /**
     * 排序
     */
    @TableField("sort")
    private Long sort;
}
