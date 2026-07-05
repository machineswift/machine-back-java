package com.machine.app.manage.ai.resource.model.controller.vo.request;

import com.machine.sdk.base.envm.StatusEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Schema
@NoArgsConstructor
public class AiResourceProviderListRequestVo {

    @Schema(description = "状态（StatusEnum），为空查全部")
    private StatusEnum status;
}
