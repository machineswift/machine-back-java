package com.machine.client.data.shop.dto.input;

import com.machine.sdk.common.envm.iam.organization.IamOrganizationTypeEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@NoArgsConstructor
public class DataShopMapByShopIdSetInputDto {

    @NotNull(message = "组织类型不能为空")
    @Schema(description = "组织类型（OrganizationTypeEnum）")
    private IamOrganizationTypeEnum organizationType;

    @NotNull(message = "门店ID集合不能为空")
    @Schema(description = "门店ID集合", requiredMode = Schema.RequiredMode.REQUIRED)
    private Set<String> shopIdSet;

    public DataShopMapByShopIdSetInputDto(IamOrganizationTypeEnum organizationType,
                                          Set<String> shopIdSet) {
        this.organizationType = organizationType;
        this.shopIdSet = shopIdSet;
    }
}
