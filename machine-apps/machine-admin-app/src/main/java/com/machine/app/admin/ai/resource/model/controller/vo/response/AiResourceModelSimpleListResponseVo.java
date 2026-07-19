package com.machine.app.admin.ai.resource.model.controller.vo.response;

import com.machine.sdk.base.envm.StatusEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Schema
@NoArgsConstructor
public class AiResourceModelSimpleListResponseVo {

    @Schema(description = "ID")
    private String id;

    @Schema(description = "状态（StatusEnum）")
    private StatusEnum status;

    @Schema(description = "厂商ID")
    private String providerId;

    @Schema(description = "模型名称")
    private String name;

    @Schema(description = "模型编码")
    private String code;
}
