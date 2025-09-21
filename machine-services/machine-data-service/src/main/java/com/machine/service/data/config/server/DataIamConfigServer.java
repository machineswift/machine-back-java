package com.machine.service.data.config.server;

import com.machine.client.data.config.IDataIamConfigClient;
import com.machine.client.data.config.dto.DataSystemConfigDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("server/data/iam_config")
public class DataIamConfigServer implements IDataIamConfigClient {

    public static final String CATEGORY = "IAM";

    @Autowired
    private DataSystemConfigServer systemConfigServer;

    @Override
    @GetMapping("sms_captcha_phone_login_limit")
    public int smsCaptchaPhoneLoginLimit() {
        String code = "SMS_CAPTCHA_PHONE_LOGIN_LIMIT";
        return systemConfigServer.getIntOrElse(new DataSystemConfigDto(CATEGORY, code), 5);
    }

    @Override
    @GetMapping("sms_captcha_forget_password_limit")
    public int smsCaptchaForgetPasswordLimit() {
        String code = "SMS_CAPTCHA_FORGET_PASSWORD_LIMIT";
        return systemConfigServer.getIntOrElse(new DataSystemConfigDto(CATEGORY, code), 5);
    }
}
