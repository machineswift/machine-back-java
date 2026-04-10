package com.machine.client.data.message.dto.input;

import com.machine.sdk.base.envm.data.message.DataMessageTemplateTypeEnum;
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
    private DataMessageTemplateTypeEnum templateType;


}