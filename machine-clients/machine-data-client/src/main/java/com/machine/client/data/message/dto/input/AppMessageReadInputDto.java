package com.machine.client.data.message.dto.input;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AppMessageReadInputDto {

    /**
     * 消息id
     */
    @NotBlank(message = "消息id不能为空")
    private String messageId;


}