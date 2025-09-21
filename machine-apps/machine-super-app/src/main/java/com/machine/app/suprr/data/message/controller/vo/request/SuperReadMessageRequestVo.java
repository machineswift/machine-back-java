package com.machine.app.suprr.data.message.controller.vo.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class SuperReadMessageRequestVo {

    /**
     * 消息id
     */
    @NotBlank(message = "消息id不能为空")
    @Schema(description = "消息id", requiredMode = Schema.RequiredMode.REQUIRED)
    private String messageId;


}