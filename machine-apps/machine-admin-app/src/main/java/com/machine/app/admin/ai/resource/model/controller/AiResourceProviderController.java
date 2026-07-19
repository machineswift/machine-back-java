package com.machine.app.admin.ai.resource.model.controller;

import cn.hutool.json.JSONUtil;
import com.machine.app.admin.ai.resource.model.business.IAiResourceProviderBusiness;
import com.machine.app.admin.ai.resource.model.controller.vo.request.AiResourceProviderCreateRequestVo;
import com.machine.app.admin.ai.resource.model.controller.vo.request.AiResourceProviderListRequestVo;
import com.machine.app.admin.ai.resource.model.controller.vo.request.AiResourceProviderUpdateRequestVo;
import com.machine.app.admin.ai.resource.model.controller.vo.request.AiResourceProviderUpdateStatusRequestVo;
import com.machine.app.admin.ai.resource.model.controller.vo.response.AiResourceProviderDetailResponseVo;
import com.machine.app.admin.ai.resource.model.controller.vo.response.AiResourceProviderExpandListResponseVo;
import com.machine.app.admin.ai.resource.model.controller.vo.response.AiResourceProviderSimpleListResponseVo;
import com.machine.sdk.base.model.request.IdRequest;
import com.machine.sdk.base.model.response.IdResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@Tag(name = "【AI】资源中心-厂商管理")
@RestController
@RequestMapping("admin/ai/resource_center/provider")
public class AiResourceProviderController {

    @Autowired
    private IAiResourceProviderBusiness resourceProviderBusiness;

    @Operation(summary = "资源中心创建厂商")
    @PostMapping("create")
    @PreAuthorize("hasAuthority('AI:RESOURCE_CENTER:PROVIDER:CREATE')")
    public IdResponse<String> create(@RequestBody @Validated AiResourceProviderCreateRequestVo request) {
        log.info("资源中心新增厂商: {}", request);
        return new IdResponse<>(resourceProviderBusiness.create(request));
    }

    @Operation(summary = "资源中心删除厂商")
    @PostMapping("delete")
    @PreAuthorize("hasAuthority('AI:RESOURCE_CENTER:PROVIDER:DELETE')")
    public void delete(@RequestBody @Validated IdRequest request) {
        log.info("资源中心删除厂商: {}", request.getId());
        resourceProviderBusiness.delete(request);
    }

    @Operation(summary = "资源中心修改厂商")
    @PostMapping("update")
    @PreAuthorize("hasAuthority('AI:RESOURCE_CENTER:PROVIDER:UPDATE')")
    public void update(@RequestBody @Validated AiResourceProviderUpdateRequestVo request) {
        log.info("资源中心修改厂商: {}", request);
        resourceProviderBusiness.update(request);
    }

    @Operation(summary = "资源中心修改厂商状态")
    @PostMapping("update_status")
    @PreAuthorize("hasAuthority('AI:RESOURCE_CENTER:PROVIDER:UPDATE_STATUS')")
    public void updateStatus(@RequestBody @Validated AiResourceProviderUpdateStatusRequestVo request) {
        log.info("资源中心修改厂商状态: {}", JSONUtil.toJsonStr(request));
        resourceProviderBusiness.updateStatus(request);
    }

    @Operation(summary = "资源中心厂商详情")
    @PostMapping("detail")
    @PreAuthorize("hasAuthority('AI:RESOURCE_CENTER:PROVIDER:DETAIL')")
    public AiResourceProviderDetailResponseVo detail(@RequestBody IdRequest request) {
        return resourceProviderBusiness.detail(request);
    }

    @Operation(summary = "资源中心查询厂商列表(用于组件弹窗/下拉选择)")
    @PostMapping("list_simple")
    @PreAuthorize("hasAuthority('AI:RESOURCE_CENTER:PROVIDER:LIST_SIMPLE')")
    public List<AiResourceProviderSimpleListResponseVo> listSimple(@RequestBody @Validated AiResourceProviderListRequestVo request) {
        return resourceProviderBusiness.listSimple(request);
    }

    @Operation(summary = "资源中心查询厂商列表(应用于管理菜单)")
    @PostMapping("list_expand")
    @PreAuthorize("hasAuthority('AI:RESOURCE_CENTER:PROVIDER:LIST_EXPAND')")
    public List<AiResourceProviderExpandListResponseVo> listExpanded(@RequestBody @Validated AiResourceProviderListRequestVo request) {
        return resourceProviderBusiness.listExpanded(request);
    }
}
