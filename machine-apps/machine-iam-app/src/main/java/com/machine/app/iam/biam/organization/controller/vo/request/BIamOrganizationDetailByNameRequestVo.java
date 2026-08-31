package com.machine.app.iam.biam.organization.controller.vo.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class BIamOrganizationDetailByNameRequestVo {

    @NotBlank(message = "parentId不能为空")
    private String parentId;

    @NotBlank(message = "名称不能为空")
    private String name;

    public BIamOrganizationDetailByNameRequestVo(String parentId,
                                                 String name) {
        this.parentId = parentId;
        this.name = name;
    }
}
