package com.machine.app.iam.organization.controller.vo.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class IamOrganizationDetailByNameRequestVo {

    @NotBlank(message = "parentId不能为空")
    private String parentId;

    @NotBlank(message = "名称不能为空")
    private String name;

    public IamOrganizationDetailByNameRequestVo(String parentId,
                                                String name) {
        this.parentId = parentId;
        this.name = name;
    }
}
