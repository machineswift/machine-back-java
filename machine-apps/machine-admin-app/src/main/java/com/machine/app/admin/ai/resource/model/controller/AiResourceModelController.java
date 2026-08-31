package com.machine.app.admin.ai.resource.model.controller;

import cn.hutool.json.JSONUtil;
import com.machine.app.admin.ai.resource.model.business.IAiResourceModelBusiness;
import com.machine.app.admin.ai.resource.model.controller.vo.request.AiResourceModelCreateRequestVo;
import com.machine.app.admin.ai.resource.model.controller.vo.request.AiResourceModelQueryPageRequestVo;
import com.machine.app.admin.ai.resource.model.controller.vo.request.AiResourceModelUpdateRequestVo;
import com.machine.app.admin.ai.resource.model.controller.vo.request.AiResourceModelUpdateStatusRequestVo;
import com.machine.app.admin.ai.resource.model.controller.vo.response.AiResourceModelDetailResponseVo;
import com.machine.app.admin.ai.resource.model.controller.vo.response.AiResourceModelExpandListResponseVo;
import com.machine.app.admin.ai.resource.model.controller.vo.response.AiResourceModelSimpleListResponseVo;
import com.machine.sdk.base.model.request.IdRequest;
import com.machine.sdk.base.model.response.IdResponse;
import com.machine.sdk.base.model.response.PageResponse;
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
@Tag(name = "【AI】资源中心-模型管理")
@RestController
@RequestMapping("admin/ai/resource_center/model")
public class AiResourceModelController {

    @Autowired
    private IAiResourceModelBusiness resourceModelBusiness;

    @Operation(summary = "资源中心创建模型")
    @PostMapping("create")
    @PreAuthorize("hasAuthority('MANAGE_APP:AI:RESOURCE_CENTER:MODEL:CREATE')")
    @WebApiAccessLog(operateSource = OperateSourceEnum.ADMIN_APP,
            module = ModuleEnum.AI,
            moduleEntity = ModuleEntityEnum.AI_MODEL,
            operateType = ActionTypeEnum.CREATE,
            operateName = "创建模型",
            responseEnable = true)
    public IdResponse<String> create(@RequestBody @Validated AiResourceModelCreateRequestVo request) {
        log.info("资源中心新增模型: {}", request);
        return new IdResponse<>(resourceModelBusiness.create(request));
    }

    @Operation(summary = "资源中心删除模型")
    @PostMapping("delete")
    @PreAuthorize("hasAuthority('MANAGE_APP:AI:RESOURCE_CENTER:MODEL:DELETE')")
    @WebApiAccessLog(operateSource = OperateSourceEnum.ADMIN_APP,
            module = ModuleEnum.AI,
            moduleEntity = ModuleEntityEnum.AI_MODEL,
            operateType = ActionTypeEnum.DELETE,
            operateName = "删除模型")
    public void delete(@RequestBody @Validated IdRequest request) {
        log.info("资源中心删除模型: {}", request.getId());
        resourceModelBusiness.delete(request);
    }

    @Operation(summary = "资源中心修改模型")
    @PostMapping("update")
    @PreAuthorize("hasAuthority('MANAGE_APP:AI:RESOURCE_CENTER:MODEL:UPDATE')")
    @WebApiAccessLog(operateSource = OperateSourceEnum.ADMIN_APP,
            module = ModuleEnum.AI,
            moduleEntity = ModuleEntityEnum.AI_MODEL,
            operateType = ActionTypeEnum.UPDATE,
            operateName = "修改模型")
    public void update(@RequestBody @Validated AiResourceModelUpdateRequestVo request) {
        log.info("资源中心修改模型: {}", request);
        resourceModelBusiness.update(request);
    }

    @Operation(summary = "资源中心修改模型状态")
    @PostMapping("update_status")
    @PreAuthorize("hasAuthority('MANAGE_APP:AI:RESOURCE_CENTER:MODEL:UPDATE_STATUS')")
    @WebApiAccessLog(operateSource = OperateSourceEnum.ADMIN_APP,
            module = ModuleEnum.AI,
            moduleEntity = ModuleEntityEnum.AI_MODEL,
            operateType = ActionTypeEnum.UPDATE,
            operateName = "修改模型状态")
    public void updateStatus(@RequestBody @Validated AiResourceModelUpdateStatusRequestVo request) {
        log.info("资源中心修改模型状态: {}", JSONUtil.toJsonStr(request));
        resourceModelBusiness.updateStatus(request);
    }

    @Operation(summary = "资源中心模型详情")
    @PostMapping("detail")
    @PreAuthorize("hasAuthority('MANAGE_APP:AI:RESOURCE_CENTER:MODEL:DETAIL')")
    @WebApiAccessLog(operateSource = OperateSourceEnum.ADMIN_APP,
            module = ModuleEnum.AI,
            moduleEntity = ModuleEntityEnum.AI_MODEL,
            operateType = ActionTypeEnum.QUERY,
            operateName = "查询模型详情")
    public AiResourceModelDetailResponseVo detail(@RequestBody IdRequest request) {
        return resourceModelBusiness.detail(request);
    }

    @Operation(summary = "资源中心分页查询模型(应用于组件弹窗)")
    @PostMapping("page_simple")
    @PreAuthorize("hasAuthority('MANAGE_APP:AI:RESOURCE_CENTER:MODEL:PAGE_SIMPLE')")
    public PageResponse<AiResourceModelSimpleListResponseVo> pageSimple(@RequestBody @Validated AiResourceModelQueryPageRequestVo request) {
        return resourceModelBusiness.pageSimple(request);
    }

    @Operation(summary = "资源中心分页查询模型(应用于管理菜单)")
    @PostMapping("page_expand")
    @PreAuthorize("hasAuthority('MANAGE_APP:AI:RESOURCE_CENTER:MODEL:PAGE_EXPAND')")
    @WebApiAccessLog(operateSource = OperateSourceEnum.ADMIN_APP,
            module = ModuleEnum.AI,
            moduleEntity = ModuleEntityEnum.AI_MODEL,
            operateType = ActionTypeEnum.QUERY,
            operateName = "分页查询模型(管理菜单)")
    public PageResponse<AiResourceModelExpandListResponseVo> pageExpand(@RequestBody @Validated AiResourceModelQueryPageRequestVo request) {
        return resourceModelBusiness.pageExpand(request);
    }
}
