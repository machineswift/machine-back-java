package com.machine.sdk.common.model.dto.data.certificate.shop;

import com.machine.sdk.common.model.dto.data.AddressInfoDto;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.validation.annotation.Validated;

import java.util.List;

/**
 * 食品经营许可证
 */
@Data
@Schema
@NoArgsConstructor
@Validated
public class DataShopFoodBusinessLicenseDto {

    @NotNull(message = "临期提醒规则不能为空")
    @Schema(description = "临期提醒规则（距离到期时间，单位:天）")
    private Integer impendingReminderRule;

    @Schema(description = "社会统一信用代码")
    private String unifiedSocialCreditCode;

    @NotNull(message = "企业名称不能为空")
    @Schema(description = "企业名称")
    private String enterpriseName;

    @Schema(description = "发证日期")
    private Long issueDate;

    @NotNull(message = "到期日期不能为空")
    @Schema(description = "到期日期")
    private Long expiryDate;

    @Schema(description = "法人名称（经营者）")
    private String legalPersonName;

    @Schema(description = "企业地址（经营地址）")
    private AddressInfoDto enterpriseAddress;

    @NotNull(message = "营食品经营许附件不能为空")
    @Schema(description = "营食品经营许附件")
    private List<String> materialIdList;
}
