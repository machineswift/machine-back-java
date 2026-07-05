package com.machine.app.manage.ai.resource.model.controller.vo.request;

import com.machine.sdk.base.model.dto.ai.AiModelFeaturesDto;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Schema
@NoArgsConstructor
public class AiResourceModelUpdateRequestVo {

    @NotBlank(message = "ID不能为空")
    @Schema(description = "ID", requiredMode = Schema.RequiredMode.REQUIRED)
    private String id;

    @Schema(description = "厂商ID")
    private String providerId;

    @Schema(description = "模型名称")
    private String name;

    @Schema(description = "模型编码")
    private String code;

    @Schema(description = "扩展特性")
    private AiModelFeaturesDto features;

    @Schema(description = "描述")
    private String description;
}
