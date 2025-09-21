package com.machine.app.openapi.data.employee.controller.vo.response;

import com.machine.sdk.common.model.dto.data.certificate.HealthCertificateDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class OpenapiShopEmployeeHealthCertificateResponseVo {
    @Schema(description = "健康证（临时:未审核）")
    private HealthCertificateDto temporaryHealthCertificate;

    @Schema(description = "健康证（永久:已审核）")
    private HealthCertificateDto permanentHealthCertificate;
}
