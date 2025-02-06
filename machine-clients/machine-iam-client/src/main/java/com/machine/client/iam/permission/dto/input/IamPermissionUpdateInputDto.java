package com.machine.client.iam.permission.dto.input;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class IamPermissionUpdateInputDto {

    @NotBlank(message = "id不能为空")
    private String id;

    @NotBlank(message = "编码不能为空")
    private String code;

    @NotBlank(message = "名称不能为空")
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
}
