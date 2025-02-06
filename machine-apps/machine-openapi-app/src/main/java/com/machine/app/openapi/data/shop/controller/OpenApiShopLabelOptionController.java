package com.machine.app.openapi.data.shop.controller;

import com.machine.app.openapi.data.shop.business.IOpenApiShopLabelBusiness;
import com.machine.app.openapi.data.shop.controller.vo.request.OpenApiShopLabelOptionListSimpleRequestVo;
import com.machine.app.openapi.data.shop.controller.vo.request.OpenApiShopLabelOptionIdRequestVo;
import com.machine.app.openapi.data.shop.controller.vo.response.OpenApiShopLabelOptionDetailResponseVo;
import com.machine.app.openapi.data.shop.controller.vo.response.OpenApiShopLabelOptionListSimpleResponseVo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@Tag(name = "【DATA】门店标签选项模块")
@RestController
@RequestMapping("openapi/data/shop_label_option")
public class OpenApiShopLabelOptionController {

    @Autowired
    private IOpenApiShopLabelBusiness shopLabelBusiness;

    @Operation(summary = "获取门店标签选项详情")
    @PostMapping("detail")
    @PreAuthorize("hasAnyAuthority('data','data_shop')")
    public OpenApiShopLabelOptionDetailResponseVo detail(@RequestBody @Valid OpenApiShopLabelOptionIdRequestVo request) {
        return shopLabelBusiness.detail(request);
    }

    @Operation(summary = "获取标签选项关联的门店ID")
    @PostMapping("list_shop_id")
    @PreAuthorize("hasAnyAuthority('data','data_shop')")
    public List<String> listShopIdByLabelOptionId(@RequestBody @Valid OpenApiShopLabelOptionIdRequestVo request) {
        return shopLabelBusiness.listShopIdByLabelOptionId(request);
    }

    @Operation(summary = "获取门店标签选项列表(基础信息)")
    @PostMapping("list_simple")
    @PreAuthorize("hasAnyAuthority('data','data_shop')")
    public List<OpenApiShopLabelOptionListSimpleResponseVo> listSimple(@RequestBody @Valid OpenApiShopLabelOptionListSimpleRequestVo request) {
        return shopLabelBusiness.listSimple(request);
    }
}