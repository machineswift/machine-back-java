package com.machine.app.openapi.data.employee.controller;

import cn.hutool.json.JSONUtil;
import com.machine.app.openapi.data.employee.business.IOpenApiShopEmployeeBusiness;
import com.machine.app.openapi.data.employee.controller.vo.request.*;
import com.machine.app.openapi.data.employee.controller.vo.response.OpenApiShopEmployeeListSimpleResponseVo;
import com.machine.app.openapi.data.employee.controller.vo.response.OpenapiShopEmployeeDetailResponseVo;
import com.machine.app.openapi.data.employee.controller.vo.response.OpenapiShopEmployeeHealthCertificateResponseVo;
import com.machine.app.openapi.data.employee.controller.vo.response.OpenapiShopEmployeeIdentityCardResponseVo;
import com.machine.app.openapi.data.franchisee.controller.vo.request.OpenApiFranchiseeUpdatePhoneRequestVo;
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
@Tag(name = "【DATA】门店员工模块")
@RestController
@RequestMapping("openapi/data/shop_employee")
public class OpenApiShopEmployeeController {

    @Autowired
    private IOpenApiShopEmployeeBusiness shopEmployeeBusiness;

    @Operation(summary = "创建")
    @PostMapping("create")
    @PreAuthorize("hasAnyAuthority('data','data_employee')")
    public IdResponse<String> create(@RequestBody @Validated OpenapiShopEmployeeCreateRequestVo request) {
        log.info("创建门店员工，clientId={} request={}", AppContext.getContext().getClientId(), JSONUtil.toJsonStr(request));
        return new IdResponse<>(shopEmployeeBusiness.create(request));
    }

    @Operation(summary = "修改门店员工状态")
    @PostMapping("update_status")
    @PreAuthorize("hasAnyAuthority('data','data_employee')")
    public void updateStatus(@RequestBody @Validated OpenapiShopEmployeeUpdateStatusRequestVo request) {
        log.info("修改门店员工状态，clientId={} request={}", AppContext.getContext().getClientId(), JSONUtil.toJsonStr(request));
        shopEmployeeBusiness.updateStatus(request);
    }

    @Operation(summary = "修改门店员工手机号")
    @PostMapping("update_phone")
    @PreAuthorize("hasAnyAuthority('data','data_franchisee')")
    public void updatePhone(@RequestBody @Validated OpenapiShopEmployeeUpdatePhoneRequestVo request) {
        log.info("修改门店员工手机号，clientId={} request={}", AppContext.getContext().getClientId(), JSONUtil.toJsonStr(request));
        shopEmployeeBusiness.updatePhone(request);
    }

    @Operation(summary = "修改身份证")
    @PostMapping("update_identity_card")
    @PreAuthorize("hasAnyAuthority('data','data_employee')")
    public void updateIdentityCard(@RequestBody @Validated OpenApiShopEmployeeUpdateIdentityCardRequestVo request) {
        log.info("修改身份证，clientId={} request={}", AppContext.getContext().getClientId(), JSONUtil.toJsonStr(request));
        shopEmployeeBusiness.updateIdentityCard(request);
    }

    @Operation(summary = "修改健康证")
    @PostMapping("update_health_certificate")
    @PreAuthorize("hasAnyAuthority('data','data_employee')")
    public void updateHealthCertificate(@RequestBody @Validated OpenApiShopEmployeeUpdateHealthCertificateRequestVo request) {
        log.info("修改健康证，clientId={} request={}", AppContext.getContext().getClientId(), JSONUtil.toJsonStr(request));
        shopEmployeeBusiness.updateHealthCertificate(request);
    }

    @Operation(summary = "修改角色（只能修改门店类型的角色，不包括加盟商角色）")
    @PostMapping("update_role")
    @PreAuthorize("hasAnyAuthority('data','data_employee')")
    public void updateRole(@RequestBody @Validated OpenApiShopEmployeeUpdateRoleRequestVo request) {
        log.info("修改角色，clientId={} request={}", AppContext.getContext().getClientId(), JSONUtil.toJsonStr(request));
        shopEmployeeBusiness.updateRole(request);
    }

    @Operation(summary = "门店员工详情")
    @PostMapping("detail")
    @PreAuthorize("hasAnyAuthority('data','data_employee')")
    public OpenapiShopEmployeeDetailResponseVo detail(@RequestBody @Valid OpenApiShopEmployeeIdRequestVo request) {
        return shopEmployeeBusiness.detail(request);
    }

    @Operation(summary = "获取身份证")
    @PostMapping("identity_card")
    @PreAuthorize("hasAnyAuthority('data','data_employee')")
    public OpenapiShopEmployeeIdentityCardResponseVo identityCard(@RequestBody @Valid OpenApiShopEmployeeIdRequestVo request) {
        return shopEmployeeBusiness.identityCard(request);
    }

    @Operation(summary = "获取健康证")
    @PostMapping("health_certificate")
    @PreAuthorize("hasAnyAuthority('data','data_employee')")
    public OpenapiShopEmployeeHealthCertificateResponseVo healthCertificate(@RequestBody @Valid OpenApiShopEmployeeIdRequestVo request) {
        return shopEmployeeBusiness.healthCertificate(request);
    }

    @Operation(summary = "获取门店员工列表(基础信息)")
    @PostMapping("list_simple")
    @PreAuthorize("hasAnyAuthority('data','data_employee')")
    public List<OpenApiShopEmployeeListSimpleResponseVo> listSimple(@RequestBody @Valid OpenApiShopEmployeeListSimpleRequestVo request) {
        return shopEmployeeBusiness.listSimple(request);
    }

}