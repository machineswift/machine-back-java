package com.machine.app.openapi.data.shop.controller;

import com.machine.app.openapi.data.shop.business.IOpenApiShopBusiness;
import com.machine.app.openapi.data.shop.controller.vo.request.*;
import com.machine.app.openapi.data.shop.controller.vo.response.*;
import com.machine.sdk.common.model.response.IdResponse;
import com.machine.sdk.common.model.response.MapResponse;
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
@Tag(name = "【DATA】门店模块")
@RestController
@RequestMapping("openapi/data/shop")
public class OpenApiShopController {

    @Autowired
    private IOpenApiShopBusiness shopBusiness;

    @Operation(summary = "获取门店ID(根据门店编码)")
    @PostMapping("id_by_code")
    @PreAuthorize("hasAnyAuthority('data','data_shop')")
    public IdResponse<String> idByCode(@RequestBody @Valid OpenApiShopCodeRequestVo request) {
        return new IdResponse<>(shopBusiness.idByCode(request));
    }

    @Operation(summary = "获取门店ID集合(根据门店编码)")
    @PostMapping("id_by_codeSet")
    @PreAuthorize("hasAnyAuthority('data','data_shop')")
    public MapResponse<String, String> idByCodeSet(@RequestBody @Valid OpenApiShopCodeSetRequestVo request) {
        return new MapResponse<>(shopBusiness.idByCodeSet(request));
    }

    @Operation(summary = "获取门店详情")
    @PostMapping("detail")
    @PreAuthorize("hasAnyAuthority('data','data_shop')")
    public OpenApiShopDetailResponseVo detail(@RequestBody @Valid OpenApiShopIdRequestVo request) {
        return shopBusiness.detail(request);
    }

    @Operation(summary = "获取门店列表(基础信息)")
    @PostMapping("list_simple")
    @PreAuthorize("hasAnyAuthority('data','data_shop')")
    public List<OpenApiShopListSimpleResponseVo> listSimple(@RequestBody @Valid OpenApiShopListSimpleRequestVo request) {
        return shopBusiness.listSimple(request);
    }

}