package com.machine.client.iam.organization.dto.input;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class IamOrganizationUpdateInputDto {

    @NotBlank(message = "id不能为空")
    private String id;

    @NotBlank(message = "名称不能为空")
    private String name;

    @Schema(description = "排序")
    private Long sort;

    @Schema(description = "描述")
    private String description;
}
