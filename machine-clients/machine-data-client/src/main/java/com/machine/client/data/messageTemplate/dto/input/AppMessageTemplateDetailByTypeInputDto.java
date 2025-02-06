package com.machine.client.data.messageTemplate.dto.input;

import com.machine.sdk.common.envm.data.MessageTemplateTypeEnum;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AppMessageTemplateDetailByTypeInputDto {

    /**
     * 模版类型
     */
    @NotNull(message = "模版类型不能为空")
    private MessageTemplateTypeEnum templateType;


}