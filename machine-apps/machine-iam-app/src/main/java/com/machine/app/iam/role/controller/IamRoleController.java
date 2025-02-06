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
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Slf4j
@Tag(name = "【IAM】角色模块")
@RestController
@RequestMapping("iam/role")
public class IamRoleController {

    @Autowired
    private IIamRoleBusiness roleBusiness;

    @Operation(summary = "创建")
    @PostMapping("create")
    public IdResponse<String> create(@RequestBody @Validated IamRoleCreateRequestVo request) {
        log.info("创建角色，request={}", JSONUtil.toJsonStr(request));
        return new IdResponse<>(roleBusiness.create(request));
    }

    @Operation(summary = "删除")
    @PostMapping("delete")
    public void delete(@RequestBody @Validated IdRequest request) {
        log.info("删除角色，request={}", JSONUtil.toJsonStr(request));
        roleBusiness.delete(request);
    }

    @Operation(summary = "修改")
    @PostMapping("update")
    public void update(@RequestBody @Validated IamRoleUpdateRequestVo request) {
        log.info("修改角色，request={}", JSONUtil.toJsonStr(request));
        roleBusiness.update(request);
    }

    @Operation(summary = "修改状态")
    @PostMapping("update_status")
    public void updateStatus(@RequestBody @Validated IamRoleUpdateStatusRequestVo request) {
        log.info("修改角色状态，request={}", JSONUtil.toJsonStr(request));
        roleBusiness.updateStatus(request);
    }

    @Operation(summary = "详情")
    @PostMapping("detail")
    public IamRoleDetailResponseVo detail(@RequestBody @Validated IdRequest request) {
        return roleBusiness.detail(request);
    }

    @Operation(summary = "分页查询(应用于组件弹窗)")
    @PostMapping("page_simpled")
    public PageResponse<IamRoleSimpleListResponseVo> pageSimple(@RequestBody @Validated IamRoleQueryPageSimpleRequestVo request) {
        return roleBusiness.pageSimple(request);
    }

    @Operation(summary = "分页查询(扩充，应用于角色管理菜单)")
    @PostMapping("page_expand")
    public PageResponse<IamRoleExpandListResponseVo> pageExpand(@RequestBody @Validated IamRoleQueryPageExpandRequestVo request) {
        return roleBusiness.pageExpand(request);
    }
}