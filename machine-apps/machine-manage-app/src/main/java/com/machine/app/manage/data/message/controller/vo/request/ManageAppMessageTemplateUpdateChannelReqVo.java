package com.machine.app.manage.data.message.controller.vo.request;

import com.machine.sdk.common.envm.data.message.MessageChannelEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@NoArgsConstructor
public class ManageAppMessageTemplateUpdateChannelReqVo{

    /**
     * 模版类型
     */
    @Schema(description = "模版id")
    @NotEmpty(message = "模版id不能为空")
    private Set<String> templateIdSet;

    /**
     * 模版状态
     */
    @Schema(description = "模版信息")
    @NotEmpty(message = "模版状态不能为空")
    private Set<MessageChannelEnum> channelEnumSet;


}