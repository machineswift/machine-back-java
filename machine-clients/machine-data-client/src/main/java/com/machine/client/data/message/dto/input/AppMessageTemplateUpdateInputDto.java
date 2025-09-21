package com.machine.client.data.message.dto.input;

import com.machine.client.data.message.dto.output.AppMessageTemplateInfoDto;
import com.machine.sdk.common.envm.data.message.DataMessageTemplateTypeEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Set;

@Data
@NoArgsConstructor
public class AppMessageTemplateUpdateInputDto{

    /**
     * 模版类型
     */
    @Schema(description = "模版类型(MessageTemplateTypeEnum)")
    @NotNull(message = "模版类型不能为空")
    private DataMessageTemplateTypeEnum templateType;

    /**
     * 通知渠道(站内信、AppPush、企微工作通知)
     */
    @Schema(description = "通知渠道(MessageChannelEnum)")
    private Set<String> channels;

    /**
     * 模版信息(通知时间、通知对象等)
     */
    @Schema(description = "模版信息")
    private AppMessageTemplateInfoDto templateInfoDto;

    /**
     * 通知标题
     */
    @Schema(description = "通知标题")
    private String informTitle;

    /**
     * 通知内容
     */
    @Schema(description = "通知内容")
    private List<AppMessageTemplateDto> informContentDto;

    /**
     * 短信内容
     */
    @Schema(description = "短信内容")
    private String smsContent;

}