package com.machine.client.data.sms.dto.input;

import com.machine.sdk.common.envm.data.sms.SmsCategoryEnum;
import com.machine.sdk.common.envm.data.sms.SmsSendResultEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class DataSmsCountRecordInputDto {

    @Schema(description = "分类")
    private SmsCategoryEnum category;

    @Schema(description = "编码")
    private String code;

    @Schema(description = "结果")
    private SmsSendResultEnum result;

    @Schema(description = "手机号")
    private String phone;

    @Schema(description = "开始时间")
    private Long startTime;
}
