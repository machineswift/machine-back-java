package com.machine.app.iam.biam.role.controller;

import cn.hutool.json.JSONUtil;
import com.machine.app.iam.biam.role.business.IBIamRoleBusiness;
import com.machine.app.iam.biam.role.controller.vo.request.*;
import com.machine.app.iam.biam.role.controller.vo.response.BIamRoleDetailResponseVo;
import com.machine.app.iam.biam.role.controller.vo.response.BIamRoleExpandListResponseVo;
import com.machine.app.iam.biam.role.controller.vo.response.BIamRoleSimpleListResponseVo;
import com.machine.sdk.base.model.request.IdRequest;
import com.machine.sdk.base.model.response.IdResponse;
import com.machine.sdk.base.model.response.PageResponse;
import com.machine.sdk.base.envm.base.ModuleEntityEnum;
import com.machine.sdk.base.envm.base.ModuleEnum;
import com.machine.sdk.base.envm.base.audit.ActionTypeEnum;
import com.machine.sdk.base.envm.base.audit.OperateSourceEnum;
import com.machine.starter.web.accessLog.annotation.WebApiAccessLog;
import com.machine.starter.web.operateLog.annotation.WebOperationLog;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Slf4j
@Tag(name = "【BIAM】角色模块")
@RestController
@RequestMapping("iam/biam/role")
public class BIamRoleController {

    @Autowired
    private IBIamRoleBusiness roleBusiness;

    @Operation(summary = "创建角色")
    @PostMapping("create")
    @PreAuthorize("hasAuthority('MANAGE_APP:SYSTEM:ACCESS_CONTROL:ROLE:CREATE')")
    @WebOperationLog(operateSource = OperateSourceEnum.IAM_APP,
            module = ModuleEnum.BIAM,
            moduleEntity = ModuleEntityEnum.BIAM_ROLE,
            operateType = ActionTypeEnum.CREATE,
            operateName = "创建角色",
            moduleEntityId = "#request.name",
            content = "'创建角色：' + #request.name",
            diff = false,
            responseEnable = true)
    public IdResponse<String> create(@RequestBody @Validated BIamRoleCreateRequestVo request) {
        log.info("创建角色，request={}", JSONUtil.toJsonStr(request));
        return new IdResponse<>(roleBusiness.create(request));
    }

    @Operation(summary = "删除角色")
    @PostMapping("delete")
    @PreAuthorize("hasAuthority('MANAGE_APP:SYSTEM:ACCESS_CONTROL:ROLE:DELETE')")
    @WebOperationLog(operateSource = OperateSourceEnum.IAM_APP,
            module = ModuleEnum.BIAM,
            moduleEntity = ModuleEntityEnum.BIAM_ROLE,
            operateType = ActionTypeEnum.DELETE,
            operateName = "删除角色",
            moduleEntityId = "#request.id",
            content = "'删除角色：' + #request.id")
    public void delete(@RequestBody @Validated IdRequest request) {
        log.info("删除角色，request={}", JSONUtil.toJsonStr(request));
        roleBusiness.delete(request);
    }

    @Operation(summary = "修改角色")
    @PostMapping("update")
    @PreAuthorize("hasAuthority('MANAGE_APP:SYSTEM:ACCESS_CONTROL:ROLE:UPDATE')")
    @WebOperationLog(operateSource = OperateSourceEnum.IAM_APP,
            module = ModuleEnum.BIAM,
            moduleEntity = ModuleEntityEnum.BIAM_ROLE,
            operateType = ActionTypeEnum.UPDATE,
            operateName = "修改角色",
            moduleEntityId = "#request.id",
            content = "'修改角色：' + #request.name")
    public void update(@RequestBody @Validated BIamRoleUpdateRequestVo request) {
        log.info("修改角色，request={}", JSONUtil.toJsonStr(request));
        roleBusiness.update(request);
    }

    @Operation(summary = "修改角色状态")
    @PostMapping("update_status")
    @PreAuthorize("hasAuthority('MANAGE_APP:SYSTEM:ACCESS_CONTROL:ROLE:UPDATE_STATUS')")
    @WebOperationLog(operateSource = OperateSourceEnum.IAM_APP,
            module = ModuleEnum.BIAM,
            moduleEntity = ModuleEntityEnum.BIAM_ROLE,
            operateType = ActionTypeEnum.UPDATE,
            operateName = "修改角色状态",
            moduleEntityId = "#request.id",
            content = "'修改角色状态：' + #request.id",
            diff = false)
    public void updateStatus(@RequestBody @Validated BIamRoleUpdateStatusRequestVo request) {
        log.info("修改角色状态，request={}", JSONUtil.toJsonStr(request));
        roleBusiness.updateStatus(request);
    }

    @Operation(summary = "修改角色权限")
    @PostMapping("update_permission")
    @PreAuthorize("hasAuthority('MANAGE_APP:SYSTEM:ACCESS_CONTROL:ROLE:UPDATE_PERMISSION')")
    @WebOperationLog(operateSource = OperateSourceEnum.IAM_APP,
            module = ModuleEnum.BIAM,
            moduleEntity = ModuleEntityEnum.BIAM_ROLE,
            operateType = ActionTypeEnum.UPDATE,
            operateName = "修改角色权限",
            moduleEntityId = "#request.id",
            content = "'修改角色权限：' + #request.id")
    public void updatePermission(@RequestBody @Validated BIamRoleUpdatePermissionRequestVo request) {
        log.info("修改角色权限，request={}", JSONUtil.toJsonStr(request));
        roleBusiness.updatePermission(request);
    }

    @Operation(summary = "角色详情")
    @PostMapping("detail")
    @PreAuthorize("hasAuthority('MANAGE_APP:SYSTEM:ACCESS_CONTROL:ROLE:DETAIL')")
    @WebApiAccessLog(operateSource = OperateSourceEnum.IAM_APP,
            module = ModuleEnum.BIAM,
            moduleEntity = ModuleEntityEnum.BIAM_ROLE,
            operateType = ActionTypeEnum.QUERY,
            operateName = "查询角色详情")
    public BIamRoleDetailResponseVo detail(@RequestBody @Validated IdRequest request) {
        return roleBusiness.detail(request);
    }

    @Operation(summary = "分页查询角色(应用于组件弹窗)")
    @PostMapping("page_simple")
    @PreAuthorize("hasAuthority('MANAGE_APP:SYSTEM:ACCESS_CONTROL:ROLE:PAGE_SIMPLE')")
    @WebApiAccessLog(operateSource = OperateSourceEnum.IAM_APP,
            module = ModuleEnum.BIAM,
            moduleEntity = ModuleEntityEnum.BIAM_ROLE,
            operateType = ActionTypeEnum.QUERY,
            operateName = "分页查询角色(组件弹窗)")
    public PageResponse<BIamRoleSimpleListResponseVo> pageSimple(
            @RequestBody @Validated BIamRoleQueryPageRequestVo request) {
        return roleBusiness.pageSimple(request);
    }

    @Operation(summary = "分页查询角色(应用于角色管理菜单)")
    @PostMapping("page_expand")
    @PreAuthorize("hasAuthority('MANAGE_APP:SYSTEM:ACCESS_CONTROL:ROLE:PAGE_EXPAND')")
    @WebApiAccessLog(operateSource = OperateSourceEnum.IAM_APP,
            module = ModuleEnum.BIAM,
            moduleEntity = ModuleEntityEnum.BIAM_ROLE,
            operateType = ActionTypeEnum.QUERY,
            operateName = "分页查询角色(角色管理菜单)")
    public PageResponse<BIamRoleExpandListResponseVo> pageExpand(
            @RequestBody @Validated BIamRoleQueryPageRequestVo request) {
        return roleBusiness.pageExpand(request);
    }
}