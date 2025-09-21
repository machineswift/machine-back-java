package com.machine.client.iam.permission.dto.input;

import com.machine.sdk.common.envm.iam.IamPermissionResourceTypeEnum;
import com.machine.sdk.common.model.dto.iam.DataPermissionMetaDto;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;

@Data
public class IamPermissionCreateInputDto {

    @Schema(description = "父id")
    @NotBlank(message = "父id不能为空")
    private String parentId;

    @Schema(description = "资源类型（IamPermissionResourceTypeEnum）")
    @NotNull(message = "资源类型不能为空")
    private IamPermissionResourceTypeEnum resourceType;

    @NotBlank(message = "编码不能为空")
    @Schema(description = "编码")
    private String code;

    @NotBlank(message = "名称不能为空")
    @Schema(description = "名称")
    private String name;

    @Schema(description = "图标")
    private String icon;

    @Schema(description = "排序")
    private Long sort;

    @Schema(description = "描述")
    private String description;

    @Schema(description = "数据权限元数据（只有菜单才有值）")
    private List<DataPermissionMetaDto> dataPermissionMetaList;

}
