package com.machine.app.iam.auth.controller;

import cn.hutool.json.JSONUtil;
import com.machine.app.iam.auth.business.IIamCurrentBusiness;
import com.machine.app.iam.auth.controller.vo.request.IamFunctionPermission4MiniRequestVo;
import com.machine.app.iam.auth.controller.vo.request.IamUserChangePasswordRequestVo;
import com.machine.app.iam.auth.controller.vo.request.IamUserSmsCaptchaChangePasswordRequestVo;
import com.machine.app.iam.auth.controller.vo.response.IamAuthCurrentUserInfoResponseVo;
import com.machine.sdk.common.context.AppContext;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.security.PermitAll;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@Tag(name = "【IAM】当前登录用户模块")
@RequestMapping("iam/current")
public class IamCurrentController {

    @Autowired
    private IIamCurrentBusiness currentBusiness;

    @PermitAll
    @Operation(summary = "用户自己修改密码")
    @PostMapping("change_password")
    public void changePassword(@RequestBody @Validated IamUserChangePasswordRequestVo request) {
        log.info("用户自己修改密码，userId={}", AppContext.getContext().getUserId());
        currentBusiness.changePassword(request);
    }

    @Operation(summary = "短信验证码修改密码")
    @PostMapping("change_password_sms_captcha")
    public void changePasswordSmsCaptcha(@RequestBody @Validated IamUserSmsCaptchaChangePasswordRequestVo request) {
        log.info("短信验证码修改密码，request={}", JSONUtil.toJsonStr(request));
        currentBusiness.changePasswordSmsCaptcha(request);
    }

    @PermitAll
    @Operation(summary = "用户信息")
    @GetMapping("user_info")
    public IamAuthCurrentUserInfoResponseVo userInfo() {
        return currentBusiness.userInfo();
    }

    @PermitAll
    @Operation(summary = "功能权限信息(权限编码集合)")
    @GetMapping("function_permission")
    public List<String> functionPermission() {
        return currentBusiness.functionPermission();
    }

    @PermitAll
    @Operation(summary = "APP功能权限信息(权限编码集合)")
    @PostMapping("function_permission_4_app")
    public List<String> functionPermission4App(@RequestBody @Validated IamFunctionPermission4MiniRequestVo request) {
        return currentBusiness.functionPermission4App(request);
    }

}
