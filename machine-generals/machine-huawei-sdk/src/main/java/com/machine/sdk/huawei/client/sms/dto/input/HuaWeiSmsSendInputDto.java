package com.machine.sdk.huawei.client.sms.dto.input;

import lombok.Data;

import java.util.List;

@Data
public class HuaWeiSmsSendInputDto {

    /**
     * 短信接收方的号码
     */
    private String to;


    /**
     * 短信模板ID
     */
    private String templateId;

    /**
     * 短信模板的变量值列表
     */
    List<String> templateParas;

}
