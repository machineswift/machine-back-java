package com.machine.app.openapi.data.message.controller.vo.request;

import com.machine.sdk.common.envm.data.message.DataMessageTemplateTypeEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Schema
@NoArgsConstructor
public class OpenApiMessageTemplateDetailByTypeRequestVo {

    @NotNull(message = "模版类型不能为空")
    @Schema(description = "模版类型(MessageTemplateTypeEnum)", requiredMode = Schema.RequiredMode.REQUIRED)
    private DataMessageTemplateTypeEnum templateType;

}