package com.machine.sdk.self.domain;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class WebHookResponseBody {

    @Schema(description = "状态码 (HttpStatus)")
    private Integer status;

    @Schema(description = "编码")
    private String code;

    @Schema(description = "消息内容")
    private String message;

    @Schema(description = "接收到消息的时间")
    private Long timestamp;

    @Schema(description = "traceId")
    private String traceId;
}