package com.machine.app.iam.auth.controller;

import cn.hutool.json.JSONUtil;
import com.machine.app.iam.auth.business.IIamAuth2Business;
import com.machine.app.iam.auth.business.IIamCurrentBusiness;
import com.machine.app.iam.auth.controller.vo.request.IamAuthChangePasswordRequestVo;
import com.machine.app.iam.auth.controller.vo.request.IamAuthSmsCaptchaChangePasswordRequestVo;
import com.machine.app.iam.auth.controller.vo.response.IamAuthCurrentUserFunctionPermissionResponseVo;
import com.machine.app.iam.auth.controller.vo.response.IamAuthCurrentUserResponseVo;
import com.machine.sdk.common.context.AppContext;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@Tag(name = "【IAM】当前登录用户模块")
@RequestMapping("iam/current")
public class IamCurrentController {

    @Autowired
    private IIamAuth2Business auth2Business;

    @Autowired
    private IIamCurrentBusiness currentBusiness;

    @Operation(summary = "绑定码云")
    @GetMapping("bind/gitee")
    public void bindGitee(HttpServletResponse response) {
        auth2Business.renderGitee(response);
    }

    @Operation(summary = "绑定飞书")
    @GetMapping("bind/fei_shu")
    public void bindFeiShu(HttpServletResponse response) {
        auth2Business.renderFeiShu(response);
    }

    @Operation(summary = "用户自己修改密码")
    @PostMapping("change_password")
    public void changePassword(@RequestBody @Validated IamAuthChangePasswordRequestVo request) {
        log.info("用户自己修改密码，userId={}", AppContext.getContext().getUserId());
        currentBusiness.changePassword(request);
    }

    @Operation(summary = "短信验证码修改密码")
    @PostMapping("change_password_sms_captcha")
    public void changePasswordSmsCaptcha(@RequestBody @Validated IamAuthSmsCaptchaChangePasswordRequestVo request) {
        log.info("短信验证码修改密码，request={}", JSONUtil.toJsonStr(request));
        currentBusiness.changePasswordSmsCaptcha(request);
    }

    @Operation(summary = "用户信息")
    @GetMapping("user_info")
    public IamAuthCurrentUserResponseVo userInfo() {
        return currentBusiness.userInfo();
    }

    @Operation(summary = "功能权限信息(权限编码集合)")
    @GetMapping("function_permission")
    public IamAuthCurrentUserFunctionPermissionResponseVo functionPermission() {
        return currentBusiness.functionPermission();
    }

}
