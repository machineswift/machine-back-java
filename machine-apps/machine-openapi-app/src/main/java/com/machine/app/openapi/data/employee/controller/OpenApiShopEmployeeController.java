package com.machine.app.openapi.data.employee.controller;

import com.machine.app.openapi.data.employee.business.IOpenApiShopEmployeeBusiness;
import com.machine.app.openapi.data.employee.controller.vo.request.*;
import com.machine.app.openapi.data.employee.controller.vo.response.OpenApiShopEmployeeListSimpleResponseVo;
import com.machine.app.openapi.data.employee.controller.vo.response.OpenapiShopEmployeeDetailResponseVo;
import com.machine.app.openapi.data.employee.controller.vo.response.OpenapiShopEmployeeHealthCertificateResponseVo;
import com.machine.app.openapi.data.employee.controller.vo.response.OpenapiShopEmployeeIdentityCardResponseVo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
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