package com.machine.client.data.message.dto.input;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class MqReliableMessageCreateInputDto {

    @NotBlank(message = "消息唯一标识不能为空")
    private String messageUuid;

    @NotBlank(message = "生产者名称不能为空")
    private String producerName;

    @NotBlank(message = "消费者名称不能为空")
    private String consumerName;

    @NotNull(message = "重发次数不能为空")
    private Integer resendTimes;

    @NotNull(message = "最后一次重发时间不能为空")
    private Long lastSendTime;

    @NotNull(message = "消费次数不能为空")
    private Integer consumerTimes;

    @NotNull(message = "最后一次消费时间不能为空")
    private Long lastConsumerTime;

    @NotBlank(message = "下一次执行时间不能为空")
    private Long nextExeTime;

    @NotBlank(message = "重试策略不能为空")
    private String retryStrategy;

    @NotBlank(message = "消息内容不能为空")
    private String content;

    @NotBlank(message = "最近一次失败原因不能为空")
    private String failReason;
}