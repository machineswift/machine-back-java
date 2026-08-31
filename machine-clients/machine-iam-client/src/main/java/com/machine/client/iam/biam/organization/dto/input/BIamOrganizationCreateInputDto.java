package com.machine.client.iam.biam.organization.dto.input;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class BIamOrganizationCreateInputDto {

    @NotBlank(message = "父id不能为空")
    private String parentId;

    @NotBlank(message = "名称不能为空")
    private String name;

    /**
     * 排序
     */
    private Long sort;

    private String description;
}
