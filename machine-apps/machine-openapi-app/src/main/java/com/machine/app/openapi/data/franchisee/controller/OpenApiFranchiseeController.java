package com.machine.app.openapi.data.franchisee.controller;

import cn.hutool.json.JSONUtil;
import com.machine.app.openapi.data.franchisee.business.IOpenApiFranchiseeBusiness;
import com.machine.app.openapi.data.franchisee.controller.vo.request.*;
import com.machine.app.openapi.data.franchisee.controller.vo.response.OpenApiFranchiseeDetailResponseVo;
import com.machine.app.openapi.data.franchisee.controller.vo.response.OpenApiFranchiseeListSampleResponseVo;
import com.machine.app.openapi.data.franchisee.controller.vo.response.OpenapiFranchiseeHealthCertificateResponseVo;
import com.machine.app.openapi.data.franchisee.controller.vo.response.OpenapiFranchiseeIdentityCardResponseVo;
import com.machine.sdk.common.context.AppContext;
import com.machine.sdk.common.model.response.IdResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@Tag(name = "【DATA】加盟商模块")
@RestController
@RequestMapping("openapi/data/franchisee")
public class OpenApiFranchiseeController {

    @Autowired
    private IOpenApiFranchiseeBusiness franchiseeBusiness;

    @Operation(summary = "创建")
    @PostMapping("create")
    @PreAuthorize("hasAnyAuthority('data','data_franchisee')")
    public IdResponse<String> create(@RequestBody @Validated OpenApiFranchiseeCreateRequestVo request) {
        log.info("创建加盟商，clientId={} request={}", AppContext.getContext().getClientId(), JSONUtil.toJsonStr(request));
        return new IdResponse<>(franchiseeBusiness.create(request));
    }

    @Operation(summary = "绑定门店")
    @PostMapping("bind_shop")
    @PreAuthorize("hasAnyAuthority('data','data_franchisee')")
    public void bindShop(@RequestBody @Validated OpenApiFranchiseeBindShopRequestVo request) {
        log.info("加盟商绑定门店，clientId={} request={}", AppContext.getContext().getClientId(), JSONUtil.toJsonStr(request));
        franchiseeBusiness.bindShop(request);
    }

    @Operation(summary = "解绑门店")
    @PostMapping("unbind_shop")
    @PreAuthorize("hasAnyAuthority('data','data_franchisee')")
    public void unbindShop(@RequestBody @Validated OpenApiFranchiseeUnbindShopRequestVo request) {
        log.info("加盟商解绑门店，clientId={} request={}", AppContext.getContext().getClientId(), JSONUtil.toJsonStr(request));
        franchiseeBusiness.unbindShop(request);
    }

    @Operation(summary = "修改手机号")
    @PostMapping("update_phone")
    @PreAuthorize("hasAnyAuthority('data','data_franchisee')")
    public void updatePhone(@RequestBody @Validated OpenApiFranchiseeUpdatePhoneRequestVo request) {
        log.info("修改加盟商手机号，clientId={} request={}", AppContext.getContext().getClientId(), JSONUtil.toJsonStr(request));
        franchiseeBusiness.updatePhone(request);
    }

    @Operation(summary = "修改")
    @PostMapping("update")
    @PreAuthorize("hasAnyAuthority('data','data_franchisee')")
    public void update(@RequestBody @Validated OpenApiFranchiseeUpdateRequestVo request) {
        log.info("修改加盟商，clientId={} request={}", AppContext.getContext().getClientId(), JSONUtil.toJsonStr(request));
        franchiseeBusiness.update(request);
    }

    @Operation(summary = "修改身份证")
    @PostMapping("update_identity_card")
    @PreAuthorize("hasAnyAuthority('data','data_employee')")
    public void updateIdentityCard(@RequestBody @Validated OpenApiFranchiseeUpdateIdentityCardRequestVo request) {
        log.info("修改身份证，clientId={} request={}", AppContext.getContext().getClientId(), JSONUtil.toJsonStr(request));
        franchiseeBusiness.updateIdentityCard(request);
    }

    @Operation(summary = "修改健康证")
    @PostMapping("update_health_certificate")
    @PreAuthorize("hasAnyAuthority('data','data_employee')")
    public void updateHealthCertificate(@RequestBody @Validated OpenApiFranchiseeUpdateHealthCertificateRequestVo request) {
        log.info("修改健康证，clientId={} request={}", AppContext.getContext().getClientId(), JSONUtil.toJsonStr(request));
        franchiseeBusiness.updateHealthCertificate(request);
    }

    @Operation(summary = "获取加盟商详情")
    @PostMapping("detail")
    @PreAuthorize("hasAnyAuthority('data','data_franchisee')")
    public OpenApiFranchiseeDetailResponseVo detail(@RequestBody @Valid OpenApiFranchiseeIdRequestVo request) {
        return franchiseeBusiness.detail(request);
    }

    @Operation(summary = "获取身份证")
    @PostMapping("identity_card")
    @PreAuthorize("hasAnyAuthority('data','data_employee')")
    public OpenapiFranchiseeIdentityCardResponseVo identityCard(@RequestBody @Valid OpenApiFranchiseeIdRequestVo request) {
        return franchiseeBusiness.identityCard(request);
    }

    @Operation(summary = "获取健康证")
    @PostMapping("health_certificate")
    @PreAuthorize("hasAnyAuthority('data','data_employee')")
    public OpenapiFranchiseeHealthCertificateResponseVo healthCertificate(@RequestBody @Valid OpenApiFranchiseeIdRequestVo request) {
        return franchiseeBusiness.healthCertificate(request);
    }

    @Operation(summary = "获取加盟商列表(基础信息)")
    @PostMapping("list_sample")
    @PreAuthorize("hasAnyAuthority('data','data_franchisee')")
    public List<OpenApiFranchiseeListSampleResponseVo> listSample(@RequestBody @Valid OpenApiFranchiseeListSampleRequestVo request) {
        return franchiseeBusiness.listSample(request);
    }

}