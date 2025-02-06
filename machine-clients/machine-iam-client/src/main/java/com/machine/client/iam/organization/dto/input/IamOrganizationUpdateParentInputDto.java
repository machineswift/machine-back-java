package com.machine.client.iam.organization.dto.input;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class IamOrganizationUpdateParentInputDto {

    @NotBlank(message = "id不能为空")
    private String id;

    @NotBlank(message = "父id不能为空")
    private String parentId;
}
