package com.machine.sdk.common.model.dto.data.certificate.shop;

import com.machine.sdk.common.model.dto.data.MaterialDto;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.validation.annotation.Validated;

import java.util.List;

/**
 * 消杀合同
 */
@Data
@Schema
@NoArgsConstructor
@Validated
public class DataShopDisinfectingContractDto {

    @NotNull(message = "临期提醒规则不能为空")
    @Schema(description = "临期提醒规则（距离到期时间，单位:天）")
    private Integer impendingReminderRule;

    @Schema(description = "合同编号")
    private String contractCode;

    @Schema(description = "合同名称")
    private String contractName;

    @Schema(description = "发证日期")
    private Long issueDate;

    @NotNull(message = "到期日期不能为空")
    @Schema(description = "到期日期")
    private Long expiryDate;

    @NotNull(message = "消杀合同附件信息不能为空")
    @Schema(description = "消杀合同附件信息")
    private List<MaterialDto> materialIdList;

}
