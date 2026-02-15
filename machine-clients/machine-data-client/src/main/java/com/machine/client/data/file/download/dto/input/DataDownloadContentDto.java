package com.machine.client.data.file.download.dto.input;

import com.machine.sdk.common.envm.base.ModuleEntityEnum;
import com.machine.sdk.common.envm.base.ModuleEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Schema
@NoArgsConstructor
public class DataDownloadContentDto {

    @NotEmpty(message = "Id不能为空")
    @Schema(description = "Id")
    private String id;

    @NotNull(message = "模块不能为空")
    @Schema(description = "模块")
    private ModuleEnum module;

    @NotNull(message = "实体不能为空")
    @Schema(description = "实体")
    private ModuleEntityEnum entity;

    @NotEmpty(message = "类名不能为空")
    @Schema(description = "类名")
    private String className;

    @NotEmpty(message = "方法名不能为空")
    @Schema(description = "方法名")
    private String methodName;

    @NotEmpty(message = "请求参数类名不能为空")
    @Schema(description = "请求参数类名")
    private String paramsClassName;

    @NotEmpty(message = "请求SON字符串不能为空")
    @Schema(description = "请求SON字符串")
    private String jsonParams;

    @Schema(description = "重试次数，默认3")
    private Integer failRetryNumber = 3;

    @Schema(description = "超时时间，默认:15")
    private Integer overTimeMinute = 15;

    @Schema(description = "调用次数")
    private Integer usageCount = 0;
}