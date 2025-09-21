package com.machine.sdk.huawei.envm;

import com.machine.sdk.common.envm.BaseEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum HuaWeiSmsStatusEnum implements BaseEnum<HuaWeiSmsStatusEnum, String> {

    SUCCESS("000000", "短信平台处理请求成功。"),

    E200015("E200015", "待发送短信数量太大。"),

    E200028("E200028", "模板变量校验失败。"),

    E200029("E200029", "模板类型校验失败。"),

    E200030("E200030", "模板未激活。"),

    E200031("E200031", "协议校验失败。"),

    E200033("E200033", "模板类型不正确。"),

    E200041("E200041", "同一短信内容接收号码重复。"),

    E200048("E200048", "频率管控。");


    private final String code;
    private final String message;

    @Override
    public String getName() {
        return this.name();
    }
}
