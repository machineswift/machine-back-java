package com.machine.client.data.message.dto.input;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class MqReliableMessageUpdate4ExceptionInputDto {

    private String id;

//    @NotNull(message = "消费次数不能为空")
//    private Integer consumerTimes;
//
//    @NotNull(message = "最后一次消费时间不能为空")
//    private Long lastConsumerTime;

    @NotBlank(message = "下一次执行时间不能为空")
    private Long nextUnixTimestamp;

    @NotBlank(message = "最近一次失败原因不能为空")
    private String failReason;
}