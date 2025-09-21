package com.machine.app.iam.permission.controller;

import cn.hutool.json.JSONUtil;
import com.machine.app.iam.permission.business.IIamPermissionBusiness;
import com.machine.app.iam.permission.controller.vo.request.*;
import com.machine.app.iam.permission.controller.vo.response.IamPermissionDetailResponseVo;
import com.machine.app.iam.permission.controller.vo.response.IamPermissionTreeExpandResponseVo;
import com.machine.app.iam.permission.controller.vo.response.IamPermissionTreeSimpleResponseVo;
import com.machine.sdk.common.model.request.IdRequest;
import com.machine.sdk.common.model.response.IdResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Slf4j
@Tag(name = "【IAM】权限模块")
@RestController
@RequestMapping("iam/permission")
public class IamPermissionController {

    @Autowired
    private IIamPermissionBusiness permissionBusiness;

    @Operation(summary = "创建权限")
    @PostMapping("create")
    @PreAuthorize("hasAuthority('SYSTEM:AUTH:PERMISSION:CREATE')")
    public IdResponse<String> create(@RequestBody @Validated IamPermissionCreateRequestVo request) {
        log.info("创建权限，request={}", JSONUtil.toJsonStr(request));
        return new IdResponse<>(permissionBusiness.create(request));
    }

    @Operation(summary = "删除权限")
    @PostMapping("delete")
    @PreAuthorize("hasAuthority('SYSTEM:AUTH:PERMISSION:DELETE')")
    public void delete(@RequestBody @Validated IdRequest request) {
        log.info("删除权限，request={}", JSONUtil.toJsonStr(request));
        permissionBusiness.delete(request);
    }

    @Operation(summary = "修改权限")
    @PostMapping("update")
    @PreAuthorize("hasAuthority('SYSTEM:AUTH:PERMISSION:UPDATE')")
    public void update(@RequestBody @Validated IamPermissionUpdateRequestVo request) {
        log.info("修改权限，request={}", JSONUtil.toJsonStr(request));
        permissionBusiness.update(request);
    }

    @Operation(summary = "修改父权限ID")
    @PostMapping("update_parent")
    @PreAuthorize("hasAuthority('SYSTEM:AUTH:PERMISSION:UPDATE_PARENT')")
    public void updateParent(@RequestBody @Validated IamPermissionUpdateParentRequestVo request) {
        permissionBusiness.updateParent(request);
    }

    @Operation(summary = "权限详情")
    @PostMapping("detail")
    @PreAuthorize("hasAuthority('SYSTEM:AUTH:PERMISSION:DETAIL')")
    public IamPermissionDetailResponseVo detail(@RequestBody @Validated IdRequest request) {
        return permissionBusiness.detail(request);
    }

    @Operation(summary = "权限树(应用于组件弹窗)")
    @PostMapping("tree_simple")
    public IamPermissionTreeSimpleResponseVo treeSimple(@RequestBody @Validated IdRequest request) {
        return permissionBusiness.treeSimple(request);
    }

    @Operation(summary = "权限树(应用于角色管理菜单)")
    @PostMapping("tree_expand")
    @PreAuthorize("hasAuthority('SYSTEM:AUTH:PERMISSION:TREE_EXPAND')")
    public IamPermissionTreeExpandResponseVo treeExpand(@RequestBody @Validated IdRequest request) {
        return permissionBusiness.treeExpand(request);
    }

}