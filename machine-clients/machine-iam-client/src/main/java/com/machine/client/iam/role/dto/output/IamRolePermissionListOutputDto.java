package com.machine.client.iam.role.dto.output;

import lombok.Data;

@Data
public class IamRolePermissionListOutputDto {
    /**
     * ID
     */
    private String id;

    /**
     * 角色Id
     */
    private String roleId;

    /**
     * 权限Id
     */
    private String permissionId;

    /**
     * 数据权限
     */
    private String dataInto;

    /**
     * 排序
     */
    private Long sort;

    /**
     * 创建人ID
     */
    private String createBy;

    /**
     * 创建时间（Unix 时间戳）
     */
    private Long createTime;

    /**
     * 操作人ID
     */
    private String updateBy;

    /**
     * 更新时间（Unix 时间戳）
     */
    private Long updateTime;
}