package com.machine.client.data.employee.dto.output;

import com.machine.sdk.common.envm.data.DataCertificateStatusEnum;
import com.machine.sdk.common.model.dto.data.certificate.HealthCertificateDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class OpenapiShopEmployeeHealthCertificateOutputDto {

    @Schema(description = "证件状态（CertificateStatusEnum）")
    private DataCertificateStatusEnum certificateStatus;

    @Schema(description = "健康证（临时:未审核）")
    private HealthCertificateDto temporaryHealthCertificate;

    @Schema(description = "健康证（永久:已审核）")
    private HealthCertificateDto permanentHealthCertificate;
}
