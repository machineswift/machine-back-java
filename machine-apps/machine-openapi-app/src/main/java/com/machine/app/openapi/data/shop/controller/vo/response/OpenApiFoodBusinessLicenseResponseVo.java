package com.machine.app.openapi.data.shop.controller.vo.response;

import com.machine.sdk.common.envm.data.CertificateStatusEnum;
import com.machine.sdk.common.model.dto.data.certificate.shop.FoodBusinessLicenseDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class OpenApiFoodBusinessLicenseResponseVo {

    @Schema(description = "证件状态（CertificateStatusEnum）")
    private CertificateStatusEnum certificateStatus;

    @Schema(description = "食品经营许可证（临时:未审核）")
    private FoodBusinessLicenseDto temporaryBusinessLicense;

    @Schema(description = "食品经营许可证（永久:已审核）")
    private FoodBusinessLicenseDto permanentBusinessLicense;
}
