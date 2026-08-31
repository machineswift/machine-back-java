package com.machine.client.iam.biam.permission.dto.input;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class BIamPermissionUpdateParentInputDto {

    @NotBlank(message = "id不能为空")
    private String id;

    @NotNull(message = "parentId不能为空")
    private String parentId;
}
