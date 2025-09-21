package com.machine.app.iam.user.controller;

import com.machine.app.iam.user.business.IIamUserLoginLogBusiness;
import com.machine.app.iam.user.controller.vo.request.IamUserLoginLogQueryPageRequestVo;
import com.machine.app.iam.user.controller.vo.response.IamUserLoginLogDetailResponseVo;
import com.machine.app.iam.user.controller.vo.response.IamUserLoginLogExpandListResponseVo;
import com.machine.sdk.common.model.request.IdRequest;
import com.machine.sdk.common.model.response.PageResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@Tag(name = "【IAM】用户登录日志模块")
@RestController
@RequestMapping("iam/user_login_log")
public class IamUserLoginLogController {

    @Autowired
    private IIamUserLoginLogBusiness userLoginLogBusiness;

    @Operation(summary = "详情")
    @PostMapping("detail")
    @PreAuthorize("hasAuthority('SYSTEM:AUTH:LOGIN_LOG:DETAIL')")
    public IamUserLoginLogDetailResponseVo detail(@RequestBody @Validated IdRequest request) {
        return userLoginLogBusiness.detail(request);
    }

    @Operation(summary = "分页查询(应用于角色管理菜单)")
    @PostMapping("page_expand")
    @PreAuthorize("hasAuthority('SYSTEM:AUTH:LOGIN_LOG:PAGE_EXPAND')")
    public PageResponse<IamUserLoginLogExpandListResponseVo> pageExpand(@RequestBody @Validated IamUserLoginLogQueryPageRequestVo request) {
        return userLoginLogBusiness.pageExpand(request);
    }
}