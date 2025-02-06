package com.machine.app.manage.data.file.controller.vo.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Schema
@NoArgsConstructor
public class DownloadFileCreateRequestVo {

    @Schema(description = "类名")
    @NotNull(message = "类名不能为空")
    private String className;

    @Schema(description = "方法名")
    @NotNull(message = "方法名不能为空")
    private String methodName;

    @Schema(description = "请求SON字符串")
    @NotNull(message = "请求SON字符串不能为空")
    private String jsonParamsRequest;

    @Schema(description = "重试次数默认2")
    private Integer failRetryNumber = 2;

    @Schema(description = "超时时间 分钟单位 默认2")
    private Integer overTimeMinute = 2;
}