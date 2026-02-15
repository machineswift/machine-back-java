package com.machine.client.data.shop.dto.input;

import com.machine.sdk.common.envm.base.StorageTypeEnum;
import com.machine.sdk.common.model.dto.data.certificate.shop.DataShopFoodBusinessLicenseDto;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.validation.annotation.Validated;

@Data
@Schema
@Validated
@NoArgsConstructor
public class DataShopUpdateFoodBusinessLicenseInputDto {

    @NotBlank(message = "门店ID不能为空")
    @Schema(description = "门店ID", requiredMode = Schema.RequiredMode.REQUIRED)
    private String id;

    @NotNull(message = "数据持久话状态不能为空")
    @Schema(description = "数据持久话状态(StorageTypeEnum)，（未审核传:TEMPORARY，审核通过传:PERMANENT）", requiredMode = Schema.RequiredMode.REQUIRED)
    private StorageTypeEnum persistenceStatus;

    @NotNull(message = "食品经营许可证不能为空")
    @Schema(description = "食品经营许可证")
    private DataShopFoodBusinessLicenseDto foodBusinessLicense;

}
