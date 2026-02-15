package com.machine.app.iam.user.controller;

import cn.hutool.json.JSONUtil;
import com.machine.app.iam.user.controller.vo.request.IamUserUpdatePasswordRequestVo;
import com.machine.app.iam.user.business.IIamUserBusiness;
import com.machine.app.iam.user.controller.vo.request.*;
import com.machine.app.iam.user.controller.vo.response.IamUserDetailResponseVo;
import com.machine.app.iam.user.controller.vo.response.IamUserExpandListResponseVo;
import com.machine.app.iam.user.controller.vo.response.IamUserSimpleListResponseVo;
import com.machine.sdk.common.context.AppContext;
import com.machine.sdk.common.model.request.IdRequest;
import com.machine.sdk.common.model.response.IdResponse;
import com.machine.sdk.common.model.response.PageResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Slf4j
@Tag(name = "【IAM】用户模块")
@RestController
@RequestMapping("iam/user")
public class IamUserController {

    @Autowired
    private IIamUserBusiness userBusiness;

    @Operation(summary = "创建用户")
    @PostMapping("create")
    @PreAuthorize("hasAuthority('SYSTEM:AUTH:USER:CREATE')")
    public IdResponse<String> create(@RequestBody @Validated IamUserCreateRequestVo request) {
        log.info("创建用户，request={}", JSONUtil.toJsonStr(request));
        return new IdResponse<>(userBusiness.create(request));
    }

    @Operation(summary = "修改用户")
    @PostMapping("update")
    @PreAuthorize("hasAuthority('SYSTEM:AUTH:USER:UPDATE')")
    public void update(@RequestBody @Validated IamUserUpdateRequestVo request) {
        log.info("修改用户，request={}", JSONUtil.toJsonStr(request));
        userBusiness.update(request);
    }

    @Operation(summary = "修改用户状态")
    @PostMapping("update_status")
    @PreAuthorize("hasAuthority('SYSTEM:AUTH:USER:UPDATE_STATUS')")
    public void updateStatus(@RequestBody @Validated IamUserUpdateStatusRequestVo request) {
        log.info("修改用户状态，request={}", JSONUtil.toJsonStr(request));
        userBusiness.updateStatus(request);
    }

    @Operation(summary = "修改用户手机号")
    @PostMapping("update_phone")
    @PreAuthorize("hasAuthority('SYSTEM:AUTH:USER:UPDATE_PHONE')")
    public void updatePhone(@RequestBody @Validated IamUserUpdatePhoneRequestVo request) {
        log.info("修改用户手机号，request={}", JSONUtil.toJsonStr(request));
        userBusiness.updatePhone(request);
    }

    @Operation(summary = "修改用户密码")
    @PostMapping("update_password")
    @PreAuthorize("hasAuthority('SYSTEM:AUTH:USER:UPDATE_PASSWORD')")
    public void updatePassword(@RequestBody @Validated IamUserUpdatePasswordRequestVo request) {
        log.info("修改用户密码，userId={} updateId={}",
                AppContext.getContext().getUserId(), request.getId());
        userBusiness.updatePassword(request);
    }

    @Operation(summary = "修改用户权限")
    @PostMapping("update_permission")
    @PreAuthorize("hasAuthority('SYSTEM:AUTH:USER:UPDATE_PERMISSION')")
    public void updatePermission(@RequestBody @Validated IamUserUpdatePermissionRequestVo request) {
        log.info("修改用户权限，userId={} updateId={}",
                AppContext.getContext().getUserId(), request.getId());
        userBusiness.updatePermission(request);
    }

    @Operation(summary = "用户详情")
    @PostMapping("detail")
    @PreAuthorize("hasAuthority('SYSTEM:AUTH:USER:DETAIL')")
    public IamUserDetailResponseVo detail(@RequestBody @Validated IdRequest request) {
        return userBusiness.detail(request);
    }

    @Operation(summary = "分页查询用户(应用于组件弹窗)")
    @PostMapping("page_simple")
    @PreAuthorize("hasAuthority('SYSTEM:AUTH:USER:PAGE_SIMPLE')")
    public PageResponse<IamUserSimpleListResponseVo> pageSimple(@RequestBody @Validated IamUserQueryPageRequestVo request) {
        return userBusiness.pageSimple(request);
    }

    @Operation(summary = "分页查询用户(应用于管理菜单)")
    @PostMapping("page_expand")
    @PreAuthorize("hasAuthority('SYSTEM:AUTH:USER:PAGE_EXPAND')")
    public PageResponse<IamUserExpandListResponseVo> pageExpand(@RequestBody @Validated IamUserQueryPageRequestVo request) {
        return userBusiness.pageExpand(request);
    }

    @Operation(summary = "导出")
    @PostMapping("export")
    @PreAuthorize("hasAuthority('SYSTEM:AUTH:USER:EXPORT')")
    public void export(@RequestBody @Validated IamUserExportRequestVo request) {
        log.info("导出用户，request={}", JSONUtil.toJsonStr(request));
        userBusiness.export(request);
    }
}