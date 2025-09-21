package com.machine.app.iam.auth.controller;

import com.machine.app.iam.auth.business.IIamAuth2Business;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@Tag(name = "【IAM】auth2.0 认证模块")
@RequestMapping("iam/auth2")
public class IamAuth2Controller {

    @Autowired
    private IIamAuth2Business auth2Business;

    @Operation(summary = "码云授权页面")
    @GetMapping("render/gitee")
    public void renderGitee(HttpServletResponse response) {
        auth2Business.renderGitee(response);
    }

    @Operation(summary = "码云授权回调")
    @GetMapping("callback/gitee")
    public void callbackGitee(HttpServletRequest request,
                              HttpServletResponse response) {
        auth2Business.callbackGitee(request, response);
    }

    @Operation(summary = "飞书")
    @GetMapping("render/fei_shu")
    public void renderFeiShu(HttpServletResponse response) {
        auth2Business.renderFeiShu(response);
    }

    @Operation(summary = "飞书授权回调")
    @GetMapping("callback/fei_shu")
    public void callbackFeiShu(HttpServletRequest request,
                               HttpServletResponse response) {
        auth2Business.callbackFeiShu(request, response);
    }
}
