package com.machine.client.data.sms.dto.input;

import com.machine.sdk.common.envm.data.sms.DataSmsCategoryEnum;
import com.machine.sdk.common.envm.data.sms.DataSmsSendResultEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class DataSmsCountRecordInputDto {

    @Schema(description = "分类")
    private DataSmsCategoryEnum category;

    @Schema(description = "编码")
    private String code;

    @Schema(description = "结果")
    private DataSmsSendResultEnum result;

    @Schema(description = "手机号")
    private String phone;

    @Schema(description = "开始时间")
    private Long startTime;
}
