package com.machine.app.iam.permission.controller;

import cn.hutool.json.JSONUtil;
import com.machine.app.iam.permission.business.IIamPermissionBusiness;
import com.machine.app.iam.permission.controller.vo.request.*;
import com.machine.app.iam.permission.controller.vo.response.IamPermissionDetailResponseVo;
import com.machine.client.iam.permission.dto.output.PermissionTreeOutputDto;
import com.machine.sdk.common.model.request.IdRequest;
import com.machine.sdk.common.model.response.IdResponse;
import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@Tag(name = "【IAM】权限模块")
@RestController
@RequestMapping("iam/permission")
public class IamPermissionController {

    @Autowired
    private IIamPermissionBusiness permissionBusiness;

    @Operation(summary = "创建")
    @PostMapping("create")
    public IdResponse<String> create(@RequestBody @Validated IamPermissionCreateRequestVo request) {
        log.info("创建权限，request={}", JSONUtil.toJsonStr(request));
        return new IdResponse<>(permissionBusiness.create(request));
    }

    @Operation(summary = "删除")
    @PostMapping("delete")
    public void delete(@RequestBody @Validated IdRequest request) {
        log.info("删除权限，request={}", JSONUtil.toJsonStr(request));
        permissionBusiness.delete(request);
    }

    @Operation(summary = "修改")
    @PostMapping("update")
    public void update(@RequestBody @Validated IamPermissionUpdateRequestVo request) {
        log.info("修改权限，request={}", JSONUtil.toJsonStr(request));
        permissionBusiness.update(request);
    }

    @Hidden
    @Operation(summary = "修改状态")
    @PostMapping("update_status")
    public void updateStatus(@RequestBody @Validated IamPermissionUpdateStatusRequestVo request) {
        log.info("修改权限状态，request={}", JSONUtil.toJsonStr(request));
        permissionBusiness.updateStatus(request);
    }

    @Operation(summary = "修改父ID")
    @PostMapping("update_parent")
    public void updateParent(@RequestBody @Validated IamPermissionUpdateParentRequestVo request) {
        permissionBusiness.updateParent(request);
    }

    @Operation(summary = "详情")
    @PostMapping("detail")
    public IamPermissionDetailResponseVo detail(@RequestBody @Validated IdRequest request) {
        return permissionBusiness.detail(request);
    }

    @Operation(summary = "应用列表")
    @PostMapping("list_app")
    public List<PermissionTreeOutputDto> listApp(@RequestBody @Validated IamPermissionQueryAppListRequestVo request) {
        return permissionBusiness.listApp(request);
    }

    @Operation(summary = "获取子权限列表")
    @PostMapping("list_sub")
    public List<PermissionTreeOutputDto> listSub(@RequestBody @Validated IamPermissionQueryListSubRequestVo request) {
        return permissionBusiness.listSub(request);
    }

    @Operation(summary = "树")
    @PostMapping("tree")
    public PermissionTreeOutputDto tree(@RequestBody @Validated IamPermissionQueryTreeRequestVo request) {
        return permissionBusiness.tree(request);
    }

}