package com.machine.app.openapi.iam.organization.controller.vo.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class OpenApiOrganizationIdRequestVo {

    @NotBlank(message = "组织ID 不能为空")
    @Schema(description = "组织ID")
    private String id;
}
