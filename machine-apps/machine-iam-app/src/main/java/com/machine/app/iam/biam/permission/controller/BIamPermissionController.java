package com.machine.app.iam.biam.permission.controller;

import cn.hutool.json.JSONUtil;
import com.machine.app.iam.biam.permission.business.IBIamPermissionBusiness;
import com.machine.app.iam.biam.permission.controller.vo.request.BIamPermissionCreateRequestVo;
import com.machine.app.iam.biam.permission.controller.vo.request.BIamPermissionUpdateParentRequestVo;
import com.machine.app.iam.biam.permission.controller.vo.request.BIamPermissionUpdateRequestVo;
import com.machine.app.iam.biam.permission.controller.vo.response.BIamPermissionDetailResponseVo;
import com.machine.app.iam.biam.permission.controller.vo.response.BIamPermissionTreeExpandResponseVo;
import com.machine.app.iam.biam.permission.controller.vo.response.BIamPermissionTreeSimpleResponseVo;
import com.machine.sdk.base.model.request.IdRequest;
import com.machine.sdk.base.model.response.IdResponse;
import com.machine.sdk.base.envm.base.ModuleEntityEnum;
import com.machine.sdk.base.envm.base.ModuleEnum;
import com.machine.sdk.base.envm.base.audit.ActionTypeEnum;
import com.machine.sdk.base.envm.base.audit.OperateSourceEnum;
import com.machine.starter.web.accessLog.annotation.WebApiAccessLog;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Slf4j
@Tag(name = "【BIAM】权限模块")
@RestController
@RequestMapping("iam/biam/permission")
public class BIamPermissionController {

    @Autowired
    private IBIamPermissionBusiness permissionBusiness;

    @Operation(summary = "创建权限")
    @PostMapping("create")
    @PreAuthorize("hasAuthority('MANAGE_APP:SYSTEM:ACCESS_CONTROL:PERMISSION:CREATE')")
    @WebApiAccessLog(operateSource = OperateSourceEnum.IAM_APP,
            module = ModuleEnum.BIAM,
            moduleEntity = ModuleEntityEnum.BIAM_PERMISSION,
            operateType = ActionTypeEnum.CREATE,
            operateName = "创建权限",
            responseEnable = true)
    public IdResponse<String> create(@RequestBody @Validated BIamPermissionCreateRequestVo request) {
        log.info("创建权限，request={}", JSONUtil.toJsonStr(request));
        return new IdResponse<>(permissionBusiness.create(request));
    }

    @Operation(summary = "删除权限")
    @PostMapping("delete")
    @PreAuthorize("hasAuthority('MANAGE_APP:SYSTEM:ACCESS_CONTROL:PERMISSION:DELETE')")
    @WebApiAccessLog(operateSource = OperateSourceEnum.IAM_APP,
            module = ModuleEnum.BIAM,
            moduleEntity = ModuleEntityEnum.BIAM_PERMISSION,
            operateType = ActionTypeEnum.DELETE,
            operateName = "删除权限")
    public void delete(@RequestBody @Validated IdRequest request) {
        log.info("删除权限，request={}", JSONUtil.toJsonStr(request));
        permissionBusiness.delete(request);
    }

    @Operation(summary = "修改权限")
    @PostMapping("update")
    @PreAuthorize("hasAuthority('MANAGE_APP:SYSTEM:ACCESS_CONTROL:PERMISSION:UPDATE')")
    @WebApiAccessLog(operateSource = OperateSourceEnum.IAM_APP,
            module = ModuleEnum.BIAM,
            moduleEntity = ModuleEntityEnum.BIAM_PERMISSION,
            operateType = ActionTypeEnum.UPDATE,
            operateName = "修改权限")
    public void update(@RequestBody @Validated BIamPermissionUpdateRequestVo request) {
        log.info("修改权限，request={}", JSONUtil.toJsonStr(request));
        permissionBusiness.update(request);
    }

    @Operation(summary = "修改父权限ID")
    @PostMapping("update_parent")
    @PreAuthorize("hasAuthority('MANAGE_APP:SYSTEM:ACCESS_CONTROL:PERMISSION:UPDATE_PARENT')")
    @WebApiAccessLog(operateSource = OperateSourceEnum.IAM_APP,
            module = ModuleEnum.BIAM,
            moduleEntity = ModuleEntityEnum.BIAM_PERMISSION,
            operateType = ActionTypeEnum.UPDATE,
            operateName = "修改父权限ID")
    public void updateParent(@RequestBody @Validated BIamPermissionUpdateParentRequestVo request) {
        permissionBusiness.updateParent(request);
    }

    @Operation(summary = "权限详情")
    @PostMapping("detail")
    @PreAuthorize("hasAuthority('MANAGE_APP:SYSTEM:ACCESS_CONTROL:PERMISSION:DETAIL')")
    @WebApiAccessLog(operateSource = OperateSourceEnum.IAM_APP,
            module = ModuleEnum.BIAM,
            moduleEntity = ModuleEntityEnum.BIAM_PERMISSION,
            operateType = ActionTypeEnum.QUERY,
            operateName = "查询权限详情")
    public BIamPermissionDetailResponseVo detail(@RequestBody @Validated IdRequest request) {
        return permissionBusiness.detail(request);
    }

    @Operation(summary = "权限树(应用于组件弹窗)")
    @PostMapping("tree_simple")
    public BIamPermissionTreeSimpleResponseVo treeSimple(@RequestBody @Validated IdRequest request) {
        return permissionBusiness.treeSimple(request);
    }

    @Operation(summary = "权限树(应用于角色管理菜单)")
    @PostMapping("tree_expand")
    @PreAuthorize("hasAuthority('MANAGE_APP:SYSTEM:ACCESS_CONTROL:PERMISSION:TREE_EXPAND')")
    @WebApiAccessLog(operateSource = OperateSourceEnum.IAM_APP,
            module = ModuleEnum.BIAM,
            moduleEntity = ModuleEntityEnum.BIAM_PERMISSION,
            operateType = ActionTypeEnum.QUERY,
            operateName = "查询权限树(角色管理菜单)")
    public BIamPermissionTreeExpandResponseVo treeExpand(@RequestBody @Validated IdRequest request) {
        return permissionBusiness.treeExpand(request);
    }

}