package com.machine.starter.mq.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class MqReliableMessageDto {
    private String id;

    /**
     * 消息唯一标识
     */
    private String messageUuid;

    /**
     * 生产者名称
     */
    private String producerName;

    /**
     * 消费者名称
     */
    private String consumerName;

    /**
     * 重发次数
     */
    private Integer resendTimes;

    /**
     * 最后一次重发时间
     */
    private Long lastSendTime;

    /**
     * 消费次数
     */
    private Integer consumerTimes;

    /**
     * 最后一次消费时间
     */
    private Long lastConsumerTime;

    /**
     * 下一次执行时间
     */
    private Long nextExeTime;

    /**
     * 重试策略
     */
    private String retryStrategy;

    /**
     * 消息内容
     */
    private String content;

    /**
     * 最近一次失败原因
     */
    private String failReason;

    /**
     * 创建人
     */
    private String createBy;

    /**
     * 创建时间
     */
    private Long createTime;

    /**
     * 修改人
     */
    private String updateBy;

    /**
     * 更新时间
     */
    private Long updateTime;
}