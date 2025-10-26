package com.machine.client.data.config;

import com.machine.sdk.common.config.OpenFeignMinTimeConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "machine-data-service", path = "machine-data-service/server/data/iam_config",
        configuration = OpenFeignMinTimeConfig.class)
public interface IDataIamConfigClient {

    @GetMapping("sms_captcha_phone_login_limit")
    int smsCaptchaPhoneLoginLimit();

    @GetMapping("sms_captcha_forget_password_limit")
    int smsCaptchaForgetPasswordLimit();

}



