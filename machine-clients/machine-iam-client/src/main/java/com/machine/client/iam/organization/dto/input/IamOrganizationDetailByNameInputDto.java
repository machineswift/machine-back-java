package com.machine.client.iam.organization.dto.input;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class IamOrganizationDetailByNameInputDto {

    @NotBlank(message = "parentId不能为空")
    private String parentId;

    @NotBlank(message = "名称不能为空")
    private String name;

    public IamOrganizationDetailByNameInputDto(String parentId,
                                               String name) {
        this.parentId = parentId;
        this.name = name;
    }
}
