package com.machine.app.suprr.data.message.controller.vo.request;

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