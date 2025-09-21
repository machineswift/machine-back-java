package com.machine.app.manage.data.shop.controller.vo.response;

import com.machine.sdk.common.envm.data.DataCertificateStatusEnum;
import com.machine.sdk.common.model.dto.data.certificate.shop.DataShopDisinfectingContractDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Schema
@NoArgsConstructor
public class DataShopDisinfectingContractResponseVo {

    @Schema(description = "证件状态（CertificateStatusEnum）")
    private DataCertificateStatusEnum certificateStatus;

    @Schema(description = "消杀合同（临时:未审核）")
    private DataShopDisinfectingContractDto temporaryDisinfectingContract;

    @Schema(description = "消杀合同（永久:已审核）")
    private DataShopDisinfectingContractDto permanentDisinfectingContract;
}
