package com.machine.app.openapi.iam.organization.controller.vo.request;

import com.machine.sdk.common.envm.iam.organization.IamOrganizationTypeEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Schema
@NoArgsConstructor
public class OpenApiOrganizationRootIdRequestVo {

    @NotNull(message = "组织类型 不能为空")
    @Schema(description = "组织类型（IamOrganizationTypeEnum）")
    private IamOrganizationTypeEnum type;

}
