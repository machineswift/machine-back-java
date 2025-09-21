package com.machine.app.iam.auth.controller.vo.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Schema
@NoArgsConstructor
public class IamAuthCaptchaResponseVo {

    public IamAuthCaptchaResponseVo(String userKey,
                                    String captchaImg) {
        this.userKey = userKey;
        this.captchaImg = captchaImg;
    }

    @Schema(description = "用户key（关联验证码）")
    private String userKey;

    @Schema(description = "验证码图片 base64 编码")
    private String captchaImg;
}
