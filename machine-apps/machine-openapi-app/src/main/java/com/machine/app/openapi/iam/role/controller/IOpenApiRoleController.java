package com.machine.app.openapi.iam.role.controller;

import com.machine.app.openapi.iam.role.business.IOpenApiRoleBusiness;
import com.machine.app.openapi.iam.role.controller.vo.request.OpenApiRoleIdRequestVo;
import com.machine.app.openapi.iam.role.controller.vo.request.OpenApiRoleListSubRequestVo;
import com.machine.app.openapi.iam.role.controller.vo.request.OpenApiRoleRootRequestVo;
import com.machine.app.openapi.iam.role.controller.vo.response.OpenApiRoleDetailResponseVo;
import com.machine.app.openapi.iam.role.controller.vo.response.OpenApiRolePermissionResponseVo;
import com.machine.sdk.base.model.response.IdResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@Tag(name = "【IAM】角色模块")
@RestController
@RequestMapping("openapi/iam/role")
public class IOpenApiRoleController {

    @Autowired
    private IOpenApiRoleBusiness roleBusiness;

    @Operation(summary = "根角色ID")
    @PostMapping("root_id")
    @PreAuthorize("hasAuthority('OPENAPI_APP:IAM:ROLE:ROOT_ID')")
    public IdResponse<String> rootId(@RequestBody @Valid OpenApiRoleRootRequestVo request) {
        return new IdResponse<>(roleBusiness.rootId(request));
    }

    @Operation(summary = "获取角色详情")
    @PostMapping("detail")
    @PreAuthorize("hasAuthority('OPENAPI_APP:IAM:ROLE:DETAIL')")
    public OpenApiRoleDetailResponseVo detail(@RequestBody @Valid OpenApiRoleIdRequestVo requestVo) {
        return roleBusiness.detail(requestVo);
    }

    @Operation(summary = "获取子角色ID列表",
            description = "本接口不受授权范围限制。\n" +
                    "本接口只支持获取下一级所有角色ID列表。")
    @PostMapping("list_sub_id")
    @PreAuthorize("hasAuthority('OPENAPI_APP:IAM:ROLE:LIST_SUB_ID')")
    public List<String> listSubId(@RequestBody @Valid OpenApiRoleListSubRequestVo request) {
        return roleBusiness.listSubId(request);
    }

    @Operation(summary = "获取指定角色的所有父角色ID列表",
            description = "该接口不受通讯录权限范围限制。\n" +
                    "获取的list元素第一个是当前角色ID，最后一个是父角色ID，从左至右角色层级递增。")
    @PostMapping("list_parent_by_target")
    @PreAuthorize("hasAuthority('OPENAPI_APP:IAM:ROLE:LIST_PARENT_BY_TARGET')")
    public List<String> listParentByTarget(@RequestBody @Valid OpenApiRoleIdRequestVo requestVo) {
        return roleBusiness.listParentByTarget(requestVo);
    }

    @Operation(summary = "获取指定角色的所有权限ID列表")
    @PostMapping("list_permission_by_target")
    @PreAuthorize("hasAuthority('OPENAPI_APP:IAM:ROLE:LIST_PERMISSION_BY_TARGET')")
    public OpenApiRolePermissionResponseVo listPermissionByTarget(@RequestBody @Valid OpenApiRoleIdRequestVo requestVo) {
        return roleBusiness.listPermissionByTarget(requestVo);
    }

}