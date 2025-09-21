package com.machine.app.iam.organization.controller.vo.request;

import com.machine.sdk.common.envm.iam.organization.IamOrganizationTypeEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Schema
@NoArgsConstructor
public class IamOrganizationQueryTreeRequestVo {

    @NotNull(message = "组织类型不能为空")
    @Schema(description = "组织类型（IamOrganizationTypeEnum）", requiredMode = Schema.RequiredMode.REQUIRED)
    private IamOrganizationTypeEnum type;

    public IamOrganizationQueryTreeRequestVo(IamOrganizationTypeEnum type) {
        this.type = type;
    }
}
