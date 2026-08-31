package com.machine.app.partner.iam.organization.controller.vo.request;

import com.machine.sdk.base.envm.biam.organization.BIamOrganizationTypeEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Schema
@NoArgsConstructor
public class SupeOrganizationTreeRequestVo {

    @NotNull(message = "组织类型不能为空")
    @Schema(description = "组织类型（IamOrganizationTypeEnum）", requiredMode = Schema.RequiredMode.REQUIRED)
    private BIamOrganizationTypeEnum type;

    public SupeOrganizationTreeRequestVo(BIamOrganizationTypeEnum type) {
        this.type = type;
    }
}
