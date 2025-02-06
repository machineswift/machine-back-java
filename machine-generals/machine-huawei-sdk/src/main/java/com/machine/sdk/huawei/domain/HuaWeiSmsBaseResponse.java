package com.machine.sdk.huawei.domain;

import lombok.Data;

import java.util.Collections;
import java.util.List;

@Data
public class HuaWeiSmsBaseResponse<T> {

    /**
     * 请求返回的结果码。
     */
    private String code;

    /**
     * 请求返回的结果码描述。
     */
    private String description;

    /**
     * 结果集
     */
    protected List<T> result = Collections.emptyList();
}
