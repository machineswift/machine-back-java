package com.machine.sdk.self.domain;

import com.machine.sdk.self.envm.EventTypeEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Schema
@NoArgsConstructor
public class WebHookEventRequestBody<T> {

    private String clientId;

    @NotNull(message = "事件类型不能为空")
    @Schema(description = "事件类型（EventTypeEnum）")
    private EventTypeEnum eventType;

    @NotNull(message = "事件发生的时间不能为空")
    @Schema(description = "事件发生的时间")
    private Long eventTime;

    @NotBlank(message = "消息Id不能为空")
    @Schema(description = "消息Id（变更时生成）")
    private String messageId;

    @NotBlank(message = "traceId不能为空")
    @Schema(description = "traceId")
    private String traceId;

    @NotNull(message = "数据不能为空")
    @Schema(description = "数据")
    private T data;

    public WebHookEventRequestBody(EventTypeEnum eventType,
                                   String messageId,
                                   T data) {
        this.eventType = eventType;
        this.eventTime = System.currentTimeMillis();
        this.messageId = messageId;
        this.data = data;
    }
}