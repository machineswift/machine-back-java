package com.machine.app.iam.biam.user.controller;

import cn.hutool.json.JSONUtil;
import com.machine.app.iam.biam.user.controller.vo.request.*;
import com.machine.app.iam.biam.user.business.IBIamUserBusiness;
import com.machine.app.iam.biam.user.controller.vo.response.BIamUserDetailResponseVo;
import com.machine.app.iam.biam.user.controller.vo.response.BIamUserExpandListResponseVo;
import com.machine.app.iam.biam.user.controller.vo.response.BIamUserSimpleListResponseVo;
import com.machine.sdk.base.model.request.IdRequest;
import com.machine.sdk.base.model.response.IdResponse;
import com.machine.sdk.base.model.response.PageResponse;
import com.machine.sdk.base.envm.base.ModuleEntityEnum;
import com.machine.sdk.base.envm.base.ModuleEnum;
import com.machine.sdk.base.envm.base.audit.ActionTypeEnum;
import com.machine.sdk.base.envm.base.audit.OperateSourceEnum;
import com.machine.starter.web.operateLog.annotation.WebOperationLog;
import com.machine.starter.web.accessLog.annotation.WebApiAccessLog;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Slf4j
@Tag(name = "【BIAM】用户模块")
@RestController
@RequestMapping("iam/biam/user")
public class BIamUserController {

    @Autowired
    private IBIamUserBusiness userBusiness;

    @Operation(summary = "创建用户")
    @PostMapping("create")
    @PreAuthorize("hasAuthority('MANAGE_APP:SYSTEM:ACCESS_CONTROL:USER:CREATE')")
    @WebOperationLog(operateSource = OperateSourceEnum.IAM_APP,
            module = ModuleEnum.BIAM,
            moduleEntity = ModuleEntityEnum.BIAM_USER,
            operateType = ActionTypeEnum.CREATE,
            operateName = "创建用户",
            moduleEntityId = "#request.username",
            content = "'创建用户：' + #request.username",
            diff = false,
            responseEnable = true)
    public IdResponse<String> create(@RequestBody @Validated BIamUserCreateRequestVo request) {
        log.info("创建用户，request={}", JSONUtil.toJsonStr(request));
        return new IdResponse<>(userBusiness.create(request));
    }

    @Operation(summary = "修改用户")
    @PostMapping("update")
    @PreAuthorize("hasAuthority('MANAGE_APP:SYSTEM:ACCESS_CONTROL:USER:UPDATE')")
    @WebOperationLog(operateSource = OperateSourceEnum.IAM_APP,
            module = ModuleEnum.BIAM,
            moduleEntity = ModuleEntityEnum.BIAM_USER,
            operateType = ActionTypeEnum.UPDATE,
            operateName = "修改用户",
            moduleEntityId = "#request.id",
            content = "'修改用户：' + #request.id")
    public void update(@RequestBody @Validated BIamUserUpdateRequestVo request) {
        log.info("修改用户，request={}", JSONUtil.toJsonStr(request));
        userBusiness.update(request);
    }

    @Operation(summary = "修改用户状态")
    @PostMapping("update_status")
    @PreAuthorize("hasAuthority('MANAGE_APP:SYSTEM:ACCESS_CONTROL:USER:UPDATE_STATUS')")
    @WebOperationLog(operateSource = OperateSourceEnum.IAM_APP,
            module = ModuleEnum.BIAM,
            moduleEntity = ModuleEntityEnum.BIAM_USER,
            operateType = ActionTypeEnum.UPDATE,
            operateName = "修改用户状态",
            moduleEntityId = "#request.id",
            content = "'修改用户状态：' + #request.id",
            diff = false)
    public void updateStatus(@RequestBody @Validated BIamUserUpdateStatusRequestVo request) {
        log.info("修改用户状态，request={}", JSONUtil.toJsonStr(request));
        userBusiness.updateStatus(request);
    }

    @Operation(summary = "修改用户手机号")
    @PostMapping("update_phone")
    @PreAuthorize("hasAuthority('MANAGE_APP:SYSTEM:ACCESS_CONTROL:USER:UPDATE_PHONE')")
    @WebOperationLog(operateSource = OperateSourceEnum.IAM_APP,
            module = ModuleEnum.BIAM,
            moduleEntity = ModuleEntityEnum.BIAM_USER,
            operateType = ActionTypeEnum.UPDATE,
            operateName = "修改用户手机号",
            moduleEntityId = "#request.id",
            content = "'修改用户手机号：' + #request.id",
            diff = false)
    public void updatePhone(@RequestBody @Validated BIamUserUpdatePhoneRequestVo request) {
        log.info("修改用户手机号，request={}", JSONUtil.toJsonStr(request));
        userBusiness.updatePhone(request);
    }

    @Operation(summary = "修改用户密码")
    @PostMapping("update_password")
    @PreAuthorize("hasAuthority('MANAGE_APP:SYSTEM:ACCESS_CONTROL:USER:UPDATE_PASSWORD')")
    @WebOperationLog(operateSource = OperateSourceEnum.IAM_APP,
            module = ModuleEnum.BIAM,
            moduleEntity = ModuleEntityEnum.BIAM_USER,
            operateType = ActionTypeEnum.UPDATE,
            operateName = "修改用户密码",
            sanitizeKeys = {"newPassword"},
            moduleEntityId = "#request.id",
            content = "'修改用户密码：' + #request.id",
            diff = false)
    public void updatePassword(@RequestBody @Validated BIamUserUpdatePasswordRequestVo request) {
        log.info("修改用户密码，updateId={}", request.getId());
        userBusiness.updatePassword(request);
    }

    @Operation(summary = "修改用户权限")
    @PostMapping("update_permission")
    @PreAuthorize("hasAuthority('MANAGE_APP:SYSTEM:ACCESS_CONTROL:USER:UPDATE_PERMISSION')")
    @WebOperationLog(operateSource = OperateSourceEnum.IAM_APP,
            module = ModuleEnum.BIAM,
            moduleEntity = ModuleEntityEnum.BIAM_USER,
            operateType = ActionTypeEnum.UPDATE,
            operateName = "修改用户权限",
            moduleEntityId = "#request.id",
            content = "'修改用户权限：' + #request.id")
    public void updatePermission(@RequestBody @Validated BIamUserUpdatePermissionRequestVo request) {
        log.info("修改用户权限，updateId={}", request.getId());
        userBusiness.updatePermission(request);
    }

    @Operation(summary = "用户详情")
    @PostMapping("detail")
    @PreAuthorize("hasAuthority('MANAGE_APP:SYSTEM:ACCESS_CONTROL:USER:DETAIL')")
    @WebApiAccessLog(operateSource = OperateSourceEnum.IAM_APP,
            module = ModuleEnum.BIAM,
            moduleEntity = ModuleEntityEnum.BIAM_USER,
            operateType = ActionTypeEnum.QUERY,
            operateName = "查询用户详情")
    public BIamUserDetailResponseVo detail(@RequestBody @Validated IdRequest request) {
        return userBusiness.detail(request);
    }

    @Operation(summary = "分页查询用户(应用于组件弹窗)")
    @PostMapping("page_simple")
    @PreAuthorize("hasAuthority('MANAGE_APP:SYSTEM:ACCESS_CONTROL:USER:PAGE_SIMPLE')")
    @WebApiAccessLog(operateSource = OperateSourceEnum.IAM_APP,
            module = ModuleEnum.BIAM,
            moduleEntity = ModuleEntityEnum.BIAM_USER,
            operateType = ActionTypeEnum.QUERY,
            operateName = "分页查询用户(组件弹窗)")
    public PageResponse<BIamUserSimpleListResponseVo> pageSimple(
            @RequestBody @Validated BIamUserQueryPageRequestVo request) {
        return userBusiness.pageSimple(request);
    }

    @Operation(summary = "分页查询用户(应用于管理菜单)")
    @PostMapping("page_expand")
    @PreAuthorize("hasAuthority('MANAGE_APP:SYSTEM:ACCESS_CONTROL:USER:PAGE_EXPAND')")
    @WebApiAccessLog(operateSource = OperateSourceEnum.IAM_APP,
            module = ModuleEnum.BIAM,
            moduleEntity = ModuleEntityEnum.BIAM_USER,
            operateType = ActionTypeEnum.QUERY,
            operateName = "分页查询用户(管理菜单)")
    public PageResponse<BIamUserExpandListResponseVo> pageExpand(
            @RequestBody @Validated BIamUserQueryPageRequestVo request) {
        return userBusiness.pageExpand(request);
    }

    @Operation(summary = "导出")
    @PostMapping("export")
    @PreAuthorize("hasAuthority('MANAGE_APP:SYSTEM:ACCESS_CONTROL:USER:EXPORT')")
    @WebApiAccessLog(operateSource = OperateSourceEnum.IAM_APP,
            module = ModuleEnum.BIAM,
            moduleEntity = ModuleEntityEnum.BIAM_USER,
            operateType = ActionTypeEnum.EXPORT,
            operateName = "导出用户")
    public void export(@RequestBody @Validated BIamUserExportRequestVo request) {
        log.info("导出用户，request={}", JSONUtil.toJsonStr(request));
        userBusiness.export(request);
    }
}