package com.machine.app.partner.data.message.controller.vo.request;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class SuperAppMessageGroupCountRequestVo{
    /**
     * 是否已读
     */
    private Integer readed;

    private String channel;

}