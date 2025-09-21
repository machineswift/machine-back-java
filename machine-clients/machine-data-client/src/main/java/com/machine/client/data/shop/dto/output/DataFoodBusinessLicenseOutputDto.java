package com.machine.client.data.shop.dto.output;

import com.machine.sdk.common.envm.data.DataCertificateStatusEnum;
import com.machine.sdk.common.model.dto.data.certificate.shop.DataShopFoodBusinessLicenseDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Schema
@NoArgsConstructor
public class DataFoodBusinessLicenseOutputDto {

    @Schema(description = "证件状态（CertificateStatusEnum）")
    private DataCertificateStatusEnum certificateStatus;

    @Schema(description = "食品经营许可证（临时:未审核）")
    private DataShopFoodBusinessLicenseDto temporaryFoodBusinessLicense;

    @Schema(description = "食品经营许可证（永久:已审核）")
    private DataShopFoodBusinessLicenseDto permanentFoodBusinessLicense;
}
