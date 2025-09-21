package com.machine.app.openapi.iam.organization.controller.vo.response;

import com.machine.sdk.common.envm.iam.organization.IamOrganizationTypeEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class OpenApiOrganizationDetailResponseVo {

    @Schema(description = "组织ID")
    private String id;

    @Schema(description = "父ID")
    private String parentId;

    @Schema(description = "编码")
    private String code;

    @Schema(description = "名称")
    private String name;

    @Schema(description = "组织类型(IamOrganizationTypeEnum)")
    private IamOrganizationTypeEnum type;

    @Schema(description = "排序")
    private Long sort;
}
