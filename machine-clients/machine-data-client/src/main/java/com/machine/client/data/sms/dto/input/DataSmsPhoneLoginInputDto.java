package com.machine.client.data.sms.dto.input;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class DataSmsPhoneLoginInputDto {

    @NotNull(message = "短信接收方的号码不能为空")
    @Schema(description = "短信接收方的号码")
    private String to;

    @NotNull(message = "验证码不能为空")
    @Schema(description = "验证码")
    private String captcha;

}
