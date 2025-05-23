package com.machine.client.data.shop.dto.input;

import com.machine.sdk.common.envm.system.DataPersistenceStatusEnum;
import com.machine.sdk.common.model.dto.data.certificate.shop.ShopBusinessLicenseDto;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Schema
@NoArgsConstructor
public class OpenApiShopUpdateShopBusinessLicenseInputDto {

    @NotBlank(message = "门店ID不能为空")
    @Schema(description = "门店ID", requiredMode = Schema.RequiredMode.REQUIRED)
    private String id;

    @NotNull(message = "数据持久话状态不能为空")
    @Schema(description = "数据持久话状态(DataPersistenceStatusEnum)，（未审核传:TEMPORARY，审核通过传:PERMANENT）", requiredMode = Schema.RequiredMode.REQUIRED)
    private DataPersistenceStatusEnum persistenceStatus;

    @NotNull(message = "门店营业执照不能为空")
    @Schema(description = "门店营业执照")
    private ShopBusinessLicenseDto businessLicense;

}
