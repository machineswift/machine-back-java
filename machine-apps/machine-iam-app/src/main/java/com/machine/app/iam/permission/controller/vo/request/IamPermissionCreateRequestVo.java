package com.machine.app.iam.permission.controller.vo.request;

import com.machine.sdk.common.envm.iam.PermissionResourceTypeEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Schema
@NoArgsConstructor
public class IamPermissionCreateRequestVo {

    @Schema(description = "父id", requiredMode = Schema.RequiredMode.REQUIRED)
    private String parentId;

    @NotNull(message = "资源类型不能为空")
    @Schema(description = "资源类型（暂不支持APP和MODULE类型的资源新增）", requiredMode = Schema.RequiredMode.REQUIRED)
    private PermissionResourceTypeEnum resourceType;

    @NotBlank(message = "编码不能为空")
    @Schema(description = "编码", requiredMode = Schema.RequiredMode.REQUIRED)
    private String code;

    @NotBlank(message = "名称不能为空")
    @Schema(description = "名称", requiredMode = Schema.RequiredMode.REQUIRED)
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

}
