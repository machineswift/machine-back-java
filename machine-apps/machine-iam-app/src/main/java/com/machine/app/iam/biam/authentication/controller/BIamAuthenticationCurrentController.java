package com.machine.app.iam.biam.authentication.controller;

import cn.hutool.json.JSONUtil;
import com.machine.app.iam.biam.authentication.business.IBIamAuthenticationCurrentBusiness;
import com.machine.app.iam.biam.authentication.controller.vo.request.BIamAuthenticationChangePasswordRequestVo;
import com.machine.app.iam.biam.authentication.controller.vo.request.BIamAuthSmsCaptchaChangePasswordRequestVo;
import com.machine.app.iam.biam.authentication.controller.vo.response.BIamAuthenticationCurrentUserFunctionPermissionResponseVo;
import com.machine.app.iam.biam.authentication.controller.vo.response.BIamAuthenticationCurrentUserResponseVo;
import com.machine.sdk.base.context.AppContextHolder;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@Tag(name = "【BIAM】认证模块-当前登录用户")
@RequestMapping("iam/biam/authentication/internal")
public class BIamAuthenticationCurrentController {

    @Autowired
    private IBIamAuthenticationCurrentBusiness currentBusiness;

    @Operation(summary = "用户自己修改密码")
    @PostMapping("change_password")
    public void changePassword(@RequestBody @Validated BIamAuthenticationChangePasswordRequestVo request) {
        log.info("用户自己修改密码，userId={}", AppContextHolder.getContext().getUserId());
        currentBusiness.changePassword(request);
    }

    @Operation(summary = "短信验证码修改密码")
    @PostMapping("change_password_sms_captcha")
    public void changePasswordSmsCaptcha(@RequestBody @Validated BIamAuthSmsCaptchaChangePasswordRequestVo request) {
        log.info("短信验证码修改密码，request={}", JSONUtil.toJsonStr(request));
        currentBusiness.changePasswordSmsCaptcha(request);
    }

    @Operation(summary = "用户信息")
    @GetMapping("user_info")
    public BIamAuthenticationCurrentUserResponseVo userInfo() {
        return currentBusiness.userInfo();
    }

    @Operation(summary = "功能权限信息(权限编码集合)")
    @GetMapping("function_permission")
    public BIamAuthenticationCurrentUserFunctionPermissionResponseVo functionPermission() {
        return currentBusiness.functionPermission();
    }

}
