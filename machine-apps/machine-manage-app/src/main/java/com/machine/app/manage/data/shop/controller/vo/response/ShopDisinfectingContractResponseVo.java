package com.machine.app.manage.data.shop.controller.vo.response;

import com.machine.sdk.common.envm.data.CertificateStatusEnum;
import com.machine.sdk.common.model.dto.data.certificate.shop.DisinfectingContractDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class ShopDisinfectingContractResponseVo {

    @Schema(description = "证件状态（CertificateStatusEnum）")
    private CertificateStatusEnum certificateStatus;

    @Schema(description = "消杀合同（临时:未审核）")
    private DisinfectingContractDto temporaryContract;

    @Schema(description = "消杀合同（永久:已审核）")
    private DisinfectingContractDto permanentContract;
}
