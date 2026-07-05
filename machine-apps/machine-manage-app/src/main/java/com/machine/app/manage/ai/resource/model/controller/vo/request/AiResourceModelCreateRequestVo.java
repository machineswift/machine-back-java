package com.machine.app.manage.ai.resource.model.controller.vo.request;

import com.machine.sdk.base.model.dto.ai.AiModelFeaturesDto;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Schema
@NoArgsConstructor
public class AiResourceModelCreateRequestVo {

    @NotBlank(message = "厂商ID不能为空")
    @Schema(description = "厂商ID", requiredMode = Schema.RequiredMode.REQUIRED)
    private String providerId;

    @NotBlank(message = "模型名称不能为空")
    @Schema(description = "模型名称", requiredMode = Schema.RequiredMode.REQUIRED)
    private String name;

    @NotBlank(message = "模型编码不能为空")
    @Schema(description = "模型编码(如deepseek-v4-flash)", requiredMode = Schema.RequiredMode.REQUIRED)
    private String code;

    @Schema(description = "扩展特性")
    private AiModelFeaturesDto features;

    @Schema(description = "描述")
    private String description;
}
