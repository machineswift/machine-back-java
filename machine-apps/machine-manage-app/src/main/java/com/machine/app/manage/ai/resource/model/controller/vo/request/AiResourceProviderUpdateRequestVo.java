package com.machine.app.manage.ai.resource.model.controller.vo.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@Schema
@NoArgsConstructor
public class AiResourceProviderUpdateRequestVo {

    @NotBlank(message = "ID不能为空")
    @Schema(description = "ID", requiredMode = Schema.RequiredMode.REQUIRED)
    private String id;

    @Schema(description = "API基础地址")
    private String baseUrl;

    @ToString.Exclude
    @Schema(description = "API密钥")
    private String apiKey;

    @Schema(description = "描述")
    private String description;
}
