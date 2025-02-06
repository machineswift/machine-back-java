package com.machine.client.iam.organization.dto.input;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class IamOrganizationCreateInputDto {

    @NotBlank(message = "父id不能为空")
    private String parentId;

    @NotBlank(message = "名称不能为空")
    private String name;

    @Schema(description = "负责人Id")
    private String leaderId;

    /**
     * 排序
     */
    private Long sort;

    private String description;
}
