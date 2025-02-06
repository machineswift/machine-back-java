package com.machine.app.suprr.data.message.controller.vo.request;

import com.machine.sdk.common.envm.data.MessageChannelEnum;
import com.machine.sdk.common.envm.data.MessageTemplateTaskCategoryEnum;
import com.machine.sdk.common.model.request.PageRequest;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class SuperMessageTaskCategoryPageRequestVo extends PageRequest {

    @Schema(description = "任务管理分类(MessageTemplateTaskCategoryEnum)", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "任务管理分类不能为空")
    private MessageTemplateTaskCategoryEnum taskCategory;

    /**
     * 通知渠道(站内信、AppPush、企微工作通知)
     */
    @Schema(description = "通知渠道(MessageChannelEnum)")
    private MessageChannelEnum channel;
}