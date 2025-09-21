package com.machine.sdk.beisen.domain;

import lombok.Data;

import java.util.List;


@Data
public class BeiSenBaseResponse<T> {

    /**
     * 下一批次滚动查询ScrollId
     */
    private String scrollId;

    /**
     * 是否最后一批次数据：true表明后面没数据了，false表示后面还有数据
     */
    private Boolean isLastData;

    /**
     * 数据总数，示例：100
     */
    private Integer total;

    /**
     * 响应码（正常情况必须200）或异常错误编码（异常情况）：
     * 200-OK，417-参数异常、业务不合法，500-内部服务器异常。
     */
    private String code;

    /**
     * 结果消息
     */
    private String message;

    /**
     * 返回数据
     */
    private List<T> data;

}
