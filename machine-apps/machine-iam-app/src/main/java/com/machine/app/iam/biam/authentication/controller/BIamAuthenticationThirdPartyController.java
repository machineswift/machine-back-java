package com.machine.app.iam.biam.authentication.controller;

import com.machine.app.iam.biam.authentication.business.IBIamAuthenticationThirdBusiness;
import com.machine.sdk.base.envm.base.ModuleEntityEnum;
import com.machine.sdk.base.envm.base.ModuleEnum;
import com.machine.sdk.base.envm.base.audit.ActionTypeEnum;
import com.machine.sdk.base.envm.base.audit.OperateSourceEnum;
import com.machine.starter.web.accessLog.annotation.WebApiAccessLog;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@Tag(name = "【BIAM】认证模块-三方平台")
@RequestMapping("iam/biam/authentication/thirdParty")
public class BIamAuthenticationThirdPartyController {

    @Autowired
    private IBIamAuthenticationThirdBusiness auth2Business;

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

    @Operation(summary = "绑定码云")
    @GetMapping("bind/gitee")
    @WebApiAccessLog(operateSource = OperateSourceEnum.IAM_APP,
            module = ModuleEnum.BIAM,
            moduleEntity = ModuleEntityEnum.BIAM_USER,
            operateType = ActionTypeEnum.UPDATE,
            operateName = "绑定码云账号")
    public void bindGitee(HttpServletResponse response) {
        auth2Business.renderGitee(response);
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

    @Operation(summary = "绑定飞书")
    @GetMapping("bind/fei_shu")
    @WebApiAccessLog(operateSource = OperateSourceEnum.IAM_APP,
            module = ModuleEnum.BIAM,
            moduleEntity = ModuleEntityEnum.BIAM_USER,
            operateType = ActionTypeEnum.UPDATE,
            operateName = "绑定飞书账号")
    public void bindFeiShu(HttpServletResponse response) {
        auth2Business.renderFeiShu(response);
    }

}
