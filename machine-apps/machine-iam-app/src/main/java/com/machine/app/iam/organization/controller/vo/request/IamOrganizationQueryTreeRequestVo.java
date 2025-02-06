package com.machine.app.iam.organization.controller.vo.request;

import com.machine.sdk.common.envm.iam.OrganizationTypeEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Schema
@NoArgsConstructor
public class IamOrganizationQueryTreeRequestVo {

    @NotNull(message = "组织类型不能为空")
    @Schema(description = "组织类型（OrganizationTypeEnum）", requiredMode = Schema.RequiredMode.REQUIRED)
    private OrganizationTypeEnum type;

    public IamOrganizationQueryTreeRequestVo(OrganizationTypeEnum type) {
        this.type = type;
    }
}
