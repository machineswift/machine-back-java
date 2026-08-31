package com.machine.app.iam.biam.authentication.controller;

import cn.hutool.json.JSONUtil;
import com.machine.app.iam.biam.authentication.business.IBIamAuthenticationCaptchaBusiness;
import com.machine.app.iam.biam.authentication.controller.vo.request.BIamAuthenticationAccessTokenRequestVo;
import com.machine.app.iam.biam.authentication.controller.vo.request.BIamAuthenticationSmsCaptchaRequestVo;
import com.machine.app.iam.biam.authentication.controller.vo.response.BIamAuthenticationCaptchaResponseVo;
import com.machine.starter.security.service.model.MachineAuthenticationResult;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.security.PermitAll;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@Tag(name = "【BIAM】认证模块-验证码")
@RequestMapping("iam/biam/authentication/internal")
public class BIamAuthenticationCaptchaController {

    @Autowired
    private IBIamAuthenticationCaptchaBusiness authBusiness;

    @Operation(summary = "获取验图片证码")
    @GetMapping("picture_captcha")
    public BIamAuthenticationCaptchaResponseVo getCaptcha() {
        return authBusiness.getCaptcha();
    }

    @Operation(summary = "手机号登录获取验证码")
    @PostMapping("sms_captcha_phone_login")
    public void smsCaptchaPhoneLogin(@RequestBody @Validated BIamAuthenticationSmsCaptchaRequestVo request) {
        log.info("手机号登录获取验证码，request={}", JSONUtil.toJsonStr(request));
        authBusiness.smsCaptchaPhoneLogin(request);
    }

    @Operation(summary = "忘记密码获取验证码")
    @PostMapping("sms_captcha_forget_password")
    public void smsCaptchaForgetPassword(@RequestBody @Validated BIamAuthenticationSmsCaptchaRequestVo request) {
        log.info("忘记密码获取验证码，request={}", JSONUtil.toJsonStr(request));
        authBusiness.smsCaptchaForgetPassword(request);
    }

    @PermitAll
    @Operation(summary = "获取accessToken")
    @PostMapping("access_token")
    public MachineAuthenticationResult accessToken(
            @RequestBody @Validated BIamAuthenticationAccessTokenRequestVo request) {
        log.info("通过RefreshToken获取AccessToken");
        return authBusiness.accessToken(request);
    }
}
