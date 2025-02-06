package com.machine.client.iam.permission.dto.input;

import com.machine.sdk.common.envm.iam.PermissionResourceTypeEnum;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class IamPermissionCreateInputDto {

    /**
     * 父id
     */
    private String parentId;

    /**
     * 资源类型（暂不支持APP和MODULE类型的资源新增）
     */
    @NotNull(message = "资源类型不能为空")
    private PermissionResourceTypeEnum resourceType;

    @NotBlank(message = "编码不能为空")
    private String code;

    @NotBlank(message = "名称不能为空")
    private String name;

    private String path;

    private String iconUrl;

    private Long sort;

    /**
     * 数据权限元数据（只有菜单才有值）
     */
    private String dataMetaInto;

    /**
     * 描述
     */
    private String description;
}
