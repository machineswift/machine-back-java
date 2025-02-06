package com.machine.sdk.common.model.dto.data.certificate;

import com.machine.sdk.common.model.dto.data.MaterialDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springframework.validation.annotation.Validated;

/**
 * 健康证
 */
@Data
@Schema
@Validated
public class HealthCertificateDto {

    @Schema(description = "姓名")
    private String name;

    @Schema(description = "发证日期")
    private Long issueDate;

    @Schema(description = "到期日期")
    private Long expiryDate;

    @Schema(description = "正面图片附件信息")
    public MaterialDto frontPictureMaterial;

    @Schema(description = "反面图片附件信息")
    public MaterialDto backPictureMaterial;
}
