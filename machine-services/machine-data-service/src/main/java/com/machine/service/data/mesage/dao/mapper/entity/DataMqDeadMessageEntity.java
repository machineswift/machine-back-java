package com.machine.service.data.mesage.dao.mapper.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.machine.starter.mybatis.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@TableName("t_data_mq_dead_message")
@EqualsAndHashCode(callSuper = true)
public class DataMqDeadMessageEntity extends BaseEntity {

    /**
     * 消息唯一标识
     */
    @TableField(value = "message_uuid")
    private String messageUuid;

    /**
     * 生产者名称
     */
    @TableField(value = "producer_name")
    private String producerName;

    /**
     * 消费者名称
     */
    @TableField(value = "consumer_name")
    private String consumerName;

    /**
     * 重发次数
     */
    @TableField(value = "resend_times")
    private Integer resendTimes;

    /**
     * 最后一次重发时间
     */
    @TableField(value = "last_send_time")
    private Long lastSendTime;

    /**
     * 消费次数
     */
    @TableField(value = "consumer_times")
    private Integer consumerTimes;

    /**
     * 最后一次消费时间
     */
    @TableField(value = "last_consumer_time")
    private Long lastConsumerTime;

    /**
     * 下一次执行时间
     */
    @TableField(value = "next_exe_time")
    private Long nextExeTime;

    /**
     * 重试策略
     */
    @TableField(value = "retry_strategy")
    private String retryStrategy;
}