package com.machine.app.iam.role.controller;

import cn.hutool.json.JSONUtil;
import com.machine.app.iam.role.business.IIamRoleBusiness;
import com.machine.app.iam.role.controller.vo.request.*;
import com.machine.app.iam.role.controller.vo.response.IamRoleDetailResponseVo;
import com.machine.app.iam.role.controller.vo.response.IamRoleExpandListResponseVo;
import com.machine.app.iam.role.controller.vo.response.IamRoleSimpleListResponseVo;
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
@Tag(name = "【IAM】角色模块")
@RestController
@RequestMapping("iam/role")
public class IamRoleController {

    @Autowired
    private IIamRoleBusiness roleBusiness;

    @Operation(summary = "创建角色")
    @PostMapping("create")
    @PreAuthorize("hasAuthority('SYSTEM:AUTH:ROLE:CREATE')")
    public IdResponse<String> create(@RequestBody @Validated IamRoleCreateRequestVo request) {
        log.info("创建角色，request={}", JSONUtil.toJsonStr(request));
        return new IdResponse<>(roleBusiness.create(request));
    }

    @Operation(summary = "删除角色")
    @PostMapping("delete")
    @PreAuthorize("hasAuthority('SYSTEM:AUTH:ROLE:DELETE')")
    public void delete(@RequestBody @Validated IdRequest request) {
        log.info("删除角色，request={}", JSONUtil.toJsonStr(request));
        roleBusiness.delete(request);
    }

    @Operation(summary = "修改角色")
    @PostMapping("update")
    @PreAuthorize("hasAuthority('SYSTEM:AUTH:ROLE:UPDATE')")
    public void update(@RequestBody @Validated IamRoleUpdateRequestVo request) {
        log.info("修改角色，request={}", JSONUtil.toJsonStr(request));
        roleBusiness.update(request);
    }

    @Operation(summary = "修改角色状态")
    @PostMapping("update_status")
    @PreAuthorize("hasAuthority('SYSTEM:AUTH:ROLE:UPDATE_STATUS')")
    public void updateStatus(@RequestBody @Validated IamRoleUpdateStatusRequestVo request) {
        log.info("修改角色状态，request={}", JSONUtil.toJsonStr(request));
        roleBusiness.updateStatus(request);
    }

    @Operation(summary = "修改角色权限")
    @PostMapping("update_permission")
    @PreAuthorize("hasAuthority('SYSTEM:AUTH:ROLE:UPDATE_PERMISSION')")
    public void updatePermission(@RequestBody @Validated IamRoleUpdatePermissionRequestVo request) {
        log.info("修改角色权限，request={}", JSONUtil.toJsonStr(request));
        roleBusiness.updatePermission(request);
    }

    @Operation(summary = "角色详情")
    @PostMapping("detail")
    @PreAuthorize("hasAuthority('SYSTEM:AUTH:ROLE:DETAIL')")
    public IamRoleDetailResponseVo detail(@RequestBody @Validated IdRequest request) {
        return roleBusiness.detail(request);
    }

    @Operation(summary = "分页查询角色(应用于组件弹窗)")
    @PostMapping("page_simple")
    @PreAuthorize("hasAuthority('SYSTEM:AUTH:ROLE:PAGE_SIMPLE')")
    public PageResponse<IamRoleSimpleListResponseVo> pageSimple(@RequestBody @Validated IamRoleQueryPageRequestVo request) {
        return roleBusiness.pageSimple(request);
    }

    @Operation(summary = "分页查询角色(应用于角色管理菜单)")
    @PostMapping("page_expand")
    @PreAuthorize("hasAuthority('SYSTEM:AUTH:ROLE:PAGE_EXPAND')")
    public PageResponse<IamRoleExpandListResponseVo> pageExpand(@RequestBody @Validated IamRoleQueryPageRequestVo request) {
        return roleBusiness.pageExpand(request);
    }
}