package com.machine.app.openapi.data.message.controller.vo.request;

import com.machine.sdk.common.envm.data.message.DataMessageTemplateTypeEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@Schema
@NoArgsConstructor
public class OpenApiSendMessageRequestVo {

    @NotNull(message = "模版类型不能为空")
    @Schema(description = "模版类型(MessageTemplateTypeEnum)", requiredMode = Schema.RequiredMode.REQUIRED)
    private DataMessageTemplateTypeEnum messageTemplateTypeEnum;

    @Schema(description = "操作人id")
    private String operatorId;

    @NotEmpty(message = "接收人不能为空")
    @Schema(description = "接收人Id集合", requiredMode = Schema.RequiredMode.REQUIRED)
    private Set<String> receiverSet;

    @NotNull(message = "是否已读不能为空")
    @Schema(description = "是否已读取 0-未读，1-已读", requiredMode = Schema.RequiredMode.REQUIRED)
    private Integer readed;

    @NotBlank(message = "消息标题不能为空")
    @Schema(description = "消息标题", requiredMode = Schema.RequiredMode.REQUIRED)
    private String title;

    @NotBlank(message = "消息内容不能为空")
    @Schema(description = "消息内容", requiredMode = Schema.RequiredMode.REQUIRED)
    private String informContent;

    @NotNull(message = "是否已处理不能为空")
    @Schema(description = "是否已处理， 0-未处理， 1-已处理", requiredMode = Schema.RequiredMode.REQUIRED)
    private Integer dispose;

    @Schema(description = "通知按钮名称")
    private String informButtonName;

    @Schema(description = "后台跳转链接")
    private String manageJumpLink;

    @Schema(description = "app跳转链接")
    private String appJumpLink;
}