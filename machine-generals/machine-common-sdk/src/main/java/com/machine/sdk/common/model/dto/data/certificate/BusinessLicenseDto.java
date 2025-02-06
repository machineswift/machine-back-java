package com.machine.sdk.common.model.dto.data.certificate;

import io.swagger.v3.oas.annotations.media.Schema;
import com.machine.sdk.common.model.dto.data.AddressInfoDto;
import com.machine.sdk.common.model.dto.data.MaterialDto;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 门店营业执照
 */
@Data
@Schema
@NoArgsConstructor
public class BusinessLicenseDto {

    @Schema(description = "社会统一信用代码")
    private String unifiedSocialCreditCode;

    @NotNull(message = "企业名称不能为空")
    @Schema(description = "企业名称")
    private String enterpriseName;

    @NotNull(message = "发证日期不能为空")
    @Schema(description = "发证日期")
    private Long issueDate;

    @NotNull(message = "到期日期不能为空")
    @Schema(description = "到期日期")
    private Long expiryDate;

    @Schema(description = "法人名称（经营者）")
    private String legalPersonName;

    @Schema(description = "企业地址（经营地址）")
    private AddressInfoDto enterpriseAddress;

    @NotNull(message = "营业执照附件信息不能为空")
    @Schema(description = "营业执照附件信息")
    private List<MaterialDto> businessLicenseMaterialList;
}
