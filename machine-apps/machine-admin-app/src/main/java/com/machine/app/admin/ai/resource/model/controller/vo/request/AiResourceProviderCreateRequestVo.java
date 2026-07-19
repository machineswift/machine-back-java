package com.machine.app.admin.ai.resource.model.controller.vo.request;

import com.machine.sdk.base.envm.ai.AiProviderEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@Schema
@NoArgsConstructor
public class AiResourceProviderCreateRequestVo {

    @NotNull(message = "厂商标识不能为空")
    @Schema(description = "厂商标识(AiProviderEnum)", requiredMode = Schema.RequiredMode.REQUIRED)
    private AiProviderEnum provider;

    @NotBlank(message = "API基础地址不能为空")
    @Schema(description = "API基础地址", requiredMode = Schema.RequiredMode.REQUIRED)
    private String baseUrl;

    @ToString.Exclude
    @NotBlank(message = "API密钥不能为空")
    @Schema(description = "API密钥", requiredMode = Schema.RequiredMode.REQUIRED)
    private String apiKey;

    @Schema(description = "描述")
    private String description;
}
