package com.machine.sdk.base.model.dto.ai;


import com.machine.sdk.base.envm.ai.AiModelCapabilityEnum;
import com.machine.sdk.base.envm.ai.AiModelFeatureEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@Schema
@NoArgsConstructor
public class AiModelFeaturesDto {

    @Schema(description = "能力列表")
    private List<AiModelCapabilityEnum> capabilityList;

    @Schema(description = "特性列表")
    private List<AiModelFeatureEnum> featureList;

    @Schema(description = "温度")
    private BigDecimal temperature;

    @Schema(description = "token 限制参数")
    private TokenLimits tokenLimits;

    @Data
    @Schema
    @NoArgsConstructor
    public static class TokenLimits {

        @Schema(description = "上下文长度（输入token上限）")
        private Long contextLength;

        @Schema(description = "输出长度（输出token上限）")
        private Long outputLength;
    }
}
