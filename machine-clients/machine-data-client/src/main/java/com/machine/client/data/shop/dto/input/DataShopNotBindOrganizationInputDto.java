package com.machine.client.data.shop.dto.input;

import com.machine.sdk.common.envm.iam.organization.IamOrganizationTypeEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Schema
@NoArgsConstructor
public class DataShopNotBindOrganizationInputDto {

    @NotNull(message = "组织类型不能为空")
    @Schema(description = "组织类型（OrganizationTypeEnum）")
    private IamOrganizationTypeEnum organizationType;

    public DataShopNotBindOrganizationInputDto(IamOrganizationTypeEnum organizationType) {
        this.organizationType = organizationType;
    }
}
