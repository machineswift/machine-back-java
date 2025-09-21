package com.machine.client.data.message.dto.input;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AppMessageGroupCountInputDto{

    /**
     * 接收人id
     */
    private String receiver;

    /**
     * 是否已读
     */
    private Integer readed;

    /**
     * 渠道
     */
    private String channel;
}