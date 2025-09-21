package com.machine.app.openapi.data.message.controller.vo.request;

import com.machine.client.data.message.dto.output.AppMessageContentDto;
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
public class OpenApiMessageSaveRequestVo {

    @Schema(description = "批次编码,不传的话,系统会自动生成，同一个请求的消息发给不同的用户，作为一个批次)")
    private String batchCode;

    @NotNull(message = "模版类型不能为空(MessageTemplateTypeEnum)")
    private DataMessageTemplateTypeEnum messageTemplateTypeEnum;

    @Schema(description = "操作人id)")
    private String operatorId;

    @NotEmpty(message = "接收人id不能为空")
    @Schema(description = "接收人id)", requiredMode = Schema.RequiredMode.REQUIRED)
    private Set<String> receiverSet;

    @NotNull(message = "是否已读不能为空")
    @Schema(description = "是否已读 0-未读，1-已读)", requiredMode = Schema.RequiredMode.REQUIRED)
    private Integer readed;

    @NotBlank(message = "消息标题不能为空")
    @Schema(description = "消息标题)", requiredMode = Schema.RequiredMode.REQUIRED)
    private String title;

    @NotBlank(message = "消息内容不能为空")
    @Schema(description = "消息内容)", requiredMode = Schema.RequiredMode.REQUIRED)
    private String informContent;

    @NotNull(message = "是否已处理不能为空")
    @Schema(description = "是否已处理， 0-未处理， 1-已处理)", requiredMode = Schema.RequiredMode.REQUIRED)
    private Integer dispose;

    @Schema(description = "业务内容)", requiredMode = Schema.RequiredMode.REQUIRED)
    private AppMessageContentDto businessContentDto;

}