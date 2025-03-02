package com.machine.client.iam.permission.dto.output;

import com.machine.sdk.common.envm.StatusEnum;
import com.machine.sdk.common.envm.iam.PermissionResourceTypeEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class PermissionDetailOutputDto {

    /**
     * ID
     */
    private String id;

    /**
     * 父ID
     */
    private String parentId;

    private StatusEnum status;

    /**
     * 资源类型
     */
    private PermissionResourceTypeEnum resourceType;

    /**
     * 编码
     */
    private String code;

    /**
     * 名称
     */
    private String name;

    /**
     * 路径
     */
    private String path;

    /**
     * 图标
     */
    private String iconUrl;

    /**
     * 排序
     */
    private Long sort;

    /**
     * 数据权限元数据（只有菜单才有值）
     */
    private String dataMetaInto;

    /**
     * 描述
     */
    private String description;

    @Schema(description = "创建人ID")
    private String createBy;

    @Schema(description = "创建时间（Unix 时间戳）")
    private Long createTime;

    @Schema(description = "操作人ID")
    private String updateBy;

    @Schema(description = "更新时间（Unix 时间戳）")
    private Long updateTime;
}
