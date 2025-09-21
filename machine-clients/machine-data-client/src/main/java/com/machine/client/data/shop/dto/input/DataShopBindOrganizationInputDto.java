package com.machine.client.data.shop.dto.input;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@Schema
@NoArgsConstructor
public class DataShopBindOrganizationInputDto {

    @NotNull(message = "门店编码集合不能为空")
    @Schema(description = "门店编码集合", requiredMode = Schema.RequiredMode.REQUIRED)
    private Set<String> shopCodeSet;

    @NotNull(message = "组织ID不能为空")
    @Schema(description = "组织ID", requiredMode = Schema.RequiredMode.REQUIRED)
    private String organizationId;

    private Boolean syncYunXi;
}
