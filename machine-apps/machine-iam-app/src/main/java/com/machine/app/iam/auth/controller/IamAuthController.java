package com.machine.app.iam.auth.controller;

import cn.hutool.json.JSONUtil;
import com.machine.app.iam.auth.business.IIamAuthBusiness;
import com.machine.app.iam.auth.controller.vo.request.IamAuthAccessTokenRequestVo;
import com.machine.app.iam.auth.controller.vo.request.IamAuthSmsCaptchaRequestVo;
import com.machine.app.iam.auth.controller.vo.response.IamAuthCaptchaResponseVo;
import com.machine.sdk.common.context.AppContext;
import com.machine.starter.security.login.IamAuthLoginResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.security.PermitAll;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@Tag(name = "【IAM】认证模块")
@RequestMapping("iam/auth")
public class IamAuthController {

    @Autowired
    private IIamAuthBusiness authBusiness;

    @Operation(summary = "获取验图片证码")
    @GetMapping("picture_captcha")
    public IamAuthCaptchaResponseVo getCaptcha() {
        return authBusiness.getCaptcha();
    }

    @Operation(summary = "手机号登录获取验证码")
    @PostMapping("sms_captcha_phone_login")
    public void smsCaptchaPhoneLogin(@RequestBody @Validated IamAuthSmsCaptchaRequestVo request) {
        log.info("手机号登录获取验证码，request={}", JSONUtil.toJsonStr(request));
        authBusiness.smsCaptchaPhoneLogin(request);
    }

    @Operation(summary = "忘记密码获取验证码")
    @PostMapping("sms_captcha_forget_password")
    public void smsCaptchaForgetPassword(@RequestBody @Validated IamAuthSmsCaptchaRequestVo request) {
        log.info("忘记密码获取验证码，request={}", JSONUtil.toJsonStr(request));
        authBusiness.smsCaptchaForgetPassword(request);
    }

    @PermitAll
    @Operation(summary = "获取accessToken")
    @PostMapping("access_token")
    public IamAuthLoginResponse accessToken(@RequestBody @Validated IamAuthAccessTokenRequestVo request) {
        log.info("通过RefreshToken获取AccessToken");
        return authBusiness.accessToken(request);
    }
}
