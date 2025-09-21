package com.machine.client.data.message.dto.output;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Schema
@NoArgsConstructor
public class AppMessageTemplateInfoDto {

    @Schema(description = "通知时间")
    private String informTime;

    @Schema(description = "通知对象")
    private String informObject;

    @Schema(description = "通知触发条件")
    private String informCondition;

    @Schema(description = "通知按钮名称")
    private String informButtonName;

    @Schema(description = "后台跳转链接")
    private String manageJumpLink;

    @Schema(description = "app跳转链接")
    private String appJumpLink;

    @Schema(description = "动态参数")
    private String dynamicParameter;
}