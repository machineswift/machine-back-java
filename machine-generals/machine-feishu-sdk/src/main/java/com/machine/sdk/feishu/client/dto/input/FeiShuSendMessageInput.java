package com.machine.sdk.feishu.client.dto.input;


import lombok.Data;

import java.io.Serializable;

@Data
public class FeiShuSendMessageInput implements Serializable {

    private static final long serialVersionUID = 1L;
    /**
     * 接收的手机号数组
     */
    private String[] mobiles;


    /**
     * 消息内容文本
     */
    private String content;

    /**
     * 消息标题
     */
    private String title;

    /**
     * 超链接文案
     */
    private String hyperlinkName;

    /**
     * 超链接
     */
    private String hyperlink;

}
