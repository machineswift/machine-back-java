package com.machine.app.openapi.data.franchisee.controller.vo.response;

import com.machine.sdk.common.envm.DataCertificateTypeEnum;
import com.machine.sdk.common.envm.data.DataFranchiseeEntityTypeEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class OpenApiFranchiseeListSampleResponseVo {

    @Schema(description = "加盟商ID")
    private String id;

    @Schema(description = "用户ID")
    private String userId;

    @Schema(description = "加盟商编码（不是用户编码）")
    private String code;

    @Schema(description = "主体类型（FranchiseeEntityTypeEnum）")
    private DataFranchiseeEntityTypeEnum entityType;

    @Schema(description = "证件类型（CertificateTypeEnum）")
    private DataCertificateTypeEnum certificateType;

    @Schema(description = "证件号码")
    private String certificateNumber;

    @Schema(description = "加盟日期")
    private Long franchiseDate;
}
