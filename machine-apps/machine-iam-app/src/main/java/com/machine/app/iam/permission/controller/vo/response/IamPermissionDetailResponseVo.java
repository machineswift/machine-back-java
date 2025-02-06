package com.machine.app.iam.permission.controller.vo.response;

import com.machine.sdk.common.envm.StatusEnum;
import com.machine.sdk.common.envm.iam.PermissionResourceTypeEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class IamPermissionDetailResponseVo {

    @Schema(description = "ID")
    private String id;

    @Schema(description = "父ID")
    private String parentId;

    @Schema(description = "状态（StatusEnum）")
    private StatusEnum status;

    @Schema(description = "资源类型")
    private PermissionResourceTypeEnum resourceType;

    @NotBlank(message = "编码不能为空")
    @Schema(description = "编码")
    private String code;

    @NotBlank(message = "名称不能为空")
    @Schema(description = "名称")
    private String name;

    @Schema(description = "路径")
    private String path;

    @Schema(description = "图标")
    private String iconUrl;

    @Schema(description = "排序")
    private Long sort;

    @Schema(description = "数据权限元数据（只有菜单才有值）")
    private String dataMetaInto;

    @Schema(description = "描述")
    private String description;

    @Schema(description = "创建人姓名")
    private String createName;

    @Schema(description = "创建人ID")
    private String createBy;

    @Schema(description = "创建时间（Unix 时间戳）")
    private Long createTime;

    @Schema(description = "操作人姓名")
    private String updateName;

    @Schema(description = "操作人ID")
    private String updateBy;

    @Schema(description = "更新时间（Unix 时间戳）")
    private Long updateTime;
}
