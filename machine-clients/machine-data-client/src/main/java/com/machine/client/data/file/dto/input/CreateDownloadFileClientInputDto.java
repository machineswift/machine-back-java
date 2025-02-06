package com.machine.client.data.file.dto.input;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CreateDownloadFileClientInputDto {

    @Schema(description = "类名")
    private String className;

    @Schema(description = "方法名")
    private String methodName;

    @Schema(description = "请求SON字符串")
    private String jsonParamsRequest;

    @Schema(description = "重试次数 默认2")
    private Integer failRetryNumber = 2;

    @Schema(description = "超时时间 分钟单位 默认2")
    private Integer overTimeMinute = 2;
}