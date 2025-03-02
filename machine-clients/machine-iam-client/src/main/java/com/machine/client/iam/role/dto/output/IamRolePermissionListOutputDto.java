package com.machine.client.iam.role.dto.output;

import io.swagger.v3.oas.annotations.media.Schema;
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

    @Schema(description = "创建人ID")
    private String createBy;

    @Schema(description = "创建时间（Unix 时间戳）")
    private Long createTime;

    @Schema(description = "操作人ID")
    private String updateBy;

    @Schema(description = "更新时间（Unix 时间戳）")
    private Long updateTime;
}