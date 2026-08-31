package com.machine.client.iam.biam.role.dto.output;

import com.machine.sdk.base.model.dto.biam.auth.BIamDataPermissionRuleDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

@Data
public class BIamRolePermissionListOutputDto {
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
    @Schema(description = "菜单数据权限")
    private List<BIamDataPermissionRuleDto> dataPermissionRuleList;

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