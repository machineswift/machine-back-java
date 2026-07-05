package com.machine.app.manage.ai.resource.model.controller.vo.response;

import com.machine.sdk.base.envm.StatusEnum;
import com.machine.sdk.base.envm.ai.AiProviderEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Schema
@NoArgsConstructor
public class AiResourceProviderDetailResponseVo {

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

    @Schema(description = "创建人姓名")
    private String createName;

    @Schema(description = "创建时间（Unix 时间戳）")
    private Long createTime;

    @Schema(description = "操作人ID")
    private String updateBy;

    @Schema(description = "操作人姓名")
    private String updateName;

    @Schema(description = "更新时间（Unix 时间戳）")
    private Long updateTime;
}
