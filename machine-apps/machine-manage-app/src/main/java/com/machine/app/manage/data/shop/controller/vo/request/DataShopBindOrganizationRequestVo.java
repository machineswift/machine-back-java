package com.machine.app.manage.data.shop.controller.vo.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@Schema
@NoArgsConstructor
public class DataShopBindOrganizationRequestVo {

    @NotNull(message = "门店Id集合不能为空")
    @Schema(description = "门店Id集合", requiredMode = Schema.RequiredMode.REQUIRED)
    private Set<String> shopIdSet;

    @NotNull(message = "组织ID不能为空")
    @Schema(description = "组织ID集合", requiredMode = Schema.RequiredMode.REQUIRED)
    private String organizationId;

}
