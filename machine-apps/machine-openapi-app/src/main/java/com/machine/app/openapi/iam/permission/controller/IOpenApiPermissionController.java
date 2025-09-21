package com.machine.app.openapi.iam.permission.controller;

import com.machine.app.openapi.iam.permission.business.IOpenApiPermissionBusiness;
import com.machine.app.openapi.iam.permission.controller.vo.request.OpenApiPermissionIdRequestVo;
import com.machine.app.openapi.iam.permission.controller.vo.request.OpenApiPermissionListSubRequestVo;
import com.machine.app.openapi.iam.permission.controller.vo.request.OpenApiPermissionQueryAppListRequestVo;
import com.machine.client.iam.permission.dto.output.IamPermissionTreeOutputDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@Tag(name = "【IAM】权限模块")
@RestController
@RequestMapping("openapi/iam/permission")
public class IOpenApiPermissionController {

    @Autowired
    private IOpenApiPermissionBusiness permissionBusiness;

    @Operation(summary = "应用列表（根节点）")
    @PostMapping("list_app")
    @PreAuthorize("hasAnyAuthority('iam','iam_permission')")
    public List<IamPermissionTreeOutputDto> listApp(@RequestBody @Validated OpenApiPermissionQueryAppListRequestVo request) {
        return permissionBusiness.listApp(request);
    }
    
    @Operation(summary = "获取权限详情")
    @PostMapping("detail")
    @PreAuthorize("hasAnyAuthority('iam','iam_permission')")
    public IamPermissionTreeOutputDto detail(@RequestBody @Valid OpenApiPermissionIdRequestVo requestVo) {
        return permissionBusiness.detail(requestVo);
    }

    @Operation(summary = "获取子权限ID列表")
    @PostMapping("list_sub_id")
    @PreAuthorize("hasAnyAuthority('iam','iam_permission')")
    public List<String> listSubId(@RequestBody @Valid OpenApiPermissionListSubRequestVo request) {
        return permissionBusiness.listSubId(request);
    }

    @Operation(summary = "获取子权限列表",
            description = "本接口只支持获取当前权限的下一级权限基础信息，不支持获取当前权限下所有层级子权限。")
    @PostMapping("list_sub")
    @PreAuthorize("hasAnyAuthority('iam','iam_permission')")
    public List<IamPermissionTreeOutputDto> listSub(@RequestBody @Valid OpenApiPermissionListSubRequestVo request) {
        return permissionBusiness.listSub(request);
    }

    @Operation(summary = "获取指定权限的所有父权限ID列表",
            description = "获取的list元素第一个是当前权限ID，最后一个是父权限ID，从左至右权限层级递增。")
    @PostMapping("list_parent_by_target")
    @PreAuthorize("hasAnyAuthority('iam','iam_permission')")
    public List<String> listParentByTarget(@RequestBody @Valid OpenApiPermissionIdRequestVo requestVo) {
        return permissionBusiness.listParentByTarget(requestVo);
    }

}