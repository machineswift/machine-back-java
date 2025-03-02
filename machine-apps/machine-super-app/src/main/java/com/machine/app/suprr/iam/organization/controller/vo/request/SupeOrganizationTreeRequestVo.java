package com.machine.app.suprr.iam.organization.controller.vo.request;

import com.machine.sdk.common.envm.iam.organization.OrganizationTypeEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Schema
@NoArgsConstructor
public class SupeOrganizationTreeRequestVo {

    @NotNull(message = "组织类型不能为空")
    @Schema(description = "组织类型（OrganizationTypeEnum）", requiredMode = Schema.RequiredMode.REQUIRED)
    private OrganizationTypeEnum type;

    public SupeOrganizationTreeRequestVo(OrganizationTypeEnum type) {
        this.type = type;
    }
}
