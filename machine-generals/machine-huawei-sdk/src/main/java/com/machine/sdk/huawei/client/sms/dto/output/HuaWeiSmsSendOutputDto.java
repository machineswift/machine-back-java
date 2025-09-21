package com.machine.sdk.huawei.client.sms.dto.output;

import lombok.Data;

@Data
public class HuaWeiSmsSendOutputDto {

    /**
     * 短信的唯一标识。
     */
    private String smsMsgId;

    /**
     * 短信发送方的号码。
     */
    private String from;

    /**
     * 短信接收方的号码。
     */
    private String originTo;

    /**
     * 短信状态码。
     */
    private String status;

    /**
     * 短信状态码描述
     */
    private String statusDescription;

    /**
     * 短信资源的创建时间，即短信平台接收到客户发送短信请求的时间，为UTC时间。
     * 格式为：yyyy-MM-dd'T'HH:mm:ss'Z'。
     */
    private String createTime;

    /**
     * 短信接收方号码的国家码。
     */
    private String countryId;

    /**
     * 短信拆分条数。
     */
    private Integer total;
}
