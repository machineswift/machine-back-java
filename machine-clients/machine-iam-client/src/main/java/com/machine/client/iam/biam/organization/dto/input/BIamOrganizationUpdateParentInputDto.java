package com.machine.client.iam.biam.organization.dto.input;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class BIamOrganizationUpdateParentInputDto {

    @NotBlank(message = "id不能为空")
    private String id;

    @NotBlank(message = "父id不能为空")
    private String parentId;
}
