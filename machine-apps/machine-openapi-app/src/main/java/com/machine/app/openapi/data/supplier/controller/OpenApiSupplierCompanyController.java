package com.machine.app.openapi.data.supplier.controller;

import com.machine.app.openapi.data.supplier.business.IOpenApiSupplierCompanyBusiness;
import com.machine.app.openapi.data.supplier.controller.vo.request.OpenApiSupplierCompanyIdRequestVo;
import com.machine.app.openapi.data.supplier.controller.vo.request.OpenApiSupplierCompanyListSimpleRequestVo;
import com.machine.app.openapi.data.supplier.controller.vo.request.OpenApiSupplierIdRequestVo;
import com.machine.app.openapi.data.supplier.controller.vo.request.OpenApiSupplierListSimpleRequestVo;
import com.machine.app.openapi.data.supplier.controller.vo.response.OpenApiSupplierCompanyDetailResponseVo;
import com.machine.app.openapi.data.supplier.controller.vo.response.OpenApiSupplierCompanyListSimpleResponseVo;
import com.machine.app.openapi.data.supplier.controller.vo.response.OpenApiSupplierListSimpleResponseVo;
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
@Tag(name = "【DATA】供应商公司模块")
@RestController
@RequestMapping("openapi/data/supplier_company")
public class OpenApiSupplierCompanyController {

    @Autowired
    private IOpenApiSupplierCompanyBusiness supplierCompanyBusiness;

    @Operation(summary = "获取供应商详情")
    @PostMapping("detail")
    @PreAuthorize("hasAnyAuthority('data','data_supplier')")
    public OpenApiSupplierCompanyDetailResponseVo detail(@RequestBody @Valid OpenApiSupplierCompanyIdRequestVo request) {
        return supplierCompanyBusiness.detail(request);
    }

    @Operation(summary = "获取供应商列表(基础信息)")
    @PostMapping("list_simple")
    @PreAuthorize("hasAnyAuthority('data','data_supplier')")
    public List<OpenApiSupplierCompanyListSimpleResponseVo> listSimple(@RequestBody @Valid OpenApiSupplierCompanyListSimpleRequestVo request) {
        return supplierCompanyBusiness.listSimple(request);
    }

}