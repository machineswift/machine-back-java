package com.machine.client.ai.resource.model.dto.output;

import cn.hutool.core.util.StrUtil;
import com.machine.sdk.base.envm.StatusEnum;
import com.machine.sdk.base.envm.ai.AiProviderEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Schema
@NoArgsConstructor
public class AiResourceProviderDetailOutputDto {

    @Schema(description = "ID")
    private String id;

    @Schema(description = "状态（StatusEnum）")
    private StatusEnum status;

    @Schema(description = "厂商标识(AiProviderEnum)")
    private AiProviderEnum provider;

    @Schema(description = "API基础地址")
    private String baseUrl;

    @Schema(description = "API密钥")
    private String apiKey;

    @Schema(description = "描述")
    private String description;

    @Schema(description = "创建人ID")
    private String createBy;

    @Schema(description = "创建时间（Unix 时间戳）")
    private Long createTime;

    @Schema(description = "操作人ID")
    private String updateBy;

    @Schema(description = "更新时间（Unix 时间戳）")
    private Long updateTime;

    public String getApiKey() {
        if (StrUtil.isBlank(apiKey) || apiKey.length() < 8) {
            return "****";
        }
        int prefixLen = 4;
        int suffixLen = 4;
        int middleLen = apiKey.length() - prefixLen - suffixLen;

        if (middleLen < 8) {
            middleLen = 8;
        }
        return apiKey.substring(0, prefixLen)
                + "*".repeat(middleLen)
                + apiKey.substring(apiKey.length() - suffixLen);
    }
}
