package com.machine.app.openapi.data.supplier.controller;

import com.machine.app.openapi.data.supplier.controller.vo.request.OpenApiSupplierIdRequestVo;
import com.machine.app.openapi.data.supplier.controller.vo.request.OpenApiSupplierListSimpleRequestVo;
import com.machine.app.openapi.data.supplier.controller.vo.response.OpenApiSupplierDetailResponseVo;
import com.machine.app.openapi.data.supplier.controller.vo.response.OpenApiSupplierListSimpleResponseVo;
import com.machine.app.openapi.data.supplier.business.IOpenApiSupplierBusiness;
import com.machine.app.openapi.iam.user.controller.vo.request.OpenApiUserIdRequestVo;
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
@Tag(name = "【DATA】供应商模块")
@RestController
@RequestMapping("openapi/data/supplier")
public class OpenApiSupplierController {

    @Autowired
    private IOpenApiSupplierBusiness supplierBusiness;

    @Operation(summary = "获取供应商详情")
    @PostMapping("detail")
    @PreAuthorize("hasAnyAuthority('data','data_supplier')")
    public OpenApiSupplierDetailResponseVo detail(@RequestBody @Valid OpenApiSupplierIdRequestVo request) {
        return supplierBusiness.detail(request);
    }

    @Operation(summary = "获取供应商详情（根据用户Id）")
    @PostMapping("detail_by_userId")
    @PreAuthorize("hasAnyAuthority('data','data_supplier')")
    public OpenApiSupplierDetailResponseVo detailByUserId(@RequestBody @Valid OpenApiUserIdRequestVo request) {
        return supplierBusiness.detailByUserId(request);
    }

    @Operation(summary = "获取供应商列表(基础信息)")
    @PostMapping("list_simple")
    @PreAuthorize("hasAnyAuthority('data','data_supplier')")
    public List<OpenApiSupplierListSimpleResponseVo> listSimple(@RequestBody @Valid OpenApiSupplierListSimpleRequestVo request) {
        return supplierBusiness.listSimple(request);
    }

}