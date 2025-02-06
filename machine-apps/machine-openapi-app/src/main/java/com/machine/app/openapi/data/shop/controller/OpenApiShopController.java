package com.machine.app.openapi.data.shop.controller;

import cn.hutool.json.JSONUtil;
import com.machine.app.openapi.data.shop.business.IOpenApiShopBusiness;
import com.machine.app.openapi.data.shop.controller.vo.request.*;
import com.machine.app.openapi.data.shop.controller.vo.response.*;
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
@Tag(name = "【DATA】门店模块")
@RestController
@RequestMapping("openapi/data/shop")
public class OpenApiShopController {

    @Autowired
    private IOpenApiShopBusiness shopBusiness;

    @Operation(summary = "新增门店")
    @PostMapping("create")
    @PreAuthorize("hasAnyAuthority('data','data_shop')")
    public IdResponse<String> create(@RequestBody @Validated OpenApiShopCreateRequestVo request) {
        log.info("新增门店，clientId={} request={}", AppContext.getContext().getClientId(), JSONUtil.toJsonStr(request));
        return new IdResponse<>(shopBusiness.create(request));
    }

    @Operation(summary = "修改门店状态")
    @PostMapping("update_status")
    @PreAuthorize("hasAnyAuthority('data','data_shop')")
    public void updateStatus(@RequestBody @Validated OpenApiShopUpdateShopStatusRequestVo request) {
        log.info("修改门店状态，clientId={} request={}", AppContext.getContext().getClientId(), JSONUtil.toJsonStr(request));
        shopBusiness.updateStatus(request);
    }

    @Operation(summary = "修改门店地址信息")
    @PostMapping("update_address")
    @PreAuthorize("hasAnyAuthority('data','data_shop')")
    public void updateAddress(@RequestBody @Validated OpenApiShopUpdateAddressRequestVo request) {
        log.info("修改门店地址信息，clientId={} request={}", AppContext.getContext().getClientId(), JSONUtil.toJsonStr(request));
        shopBusiness.updateAddress(request);
    }

    @Operation(summary = "修改门店营业执照")
    @PostMapping("update_shop_business_license")
    @PreAuthorize("hasAnyAuthority('data','data_shop')")
    public void updateShopBusinessLicense(@RequestBody @Validated OpenApiShopUpdateShopBusinessLicenseRequestVo request) {
        log.info("修改门店营业执照，clientId={} request={}", AppContext.getContext().getClientId(), JSONUtil.toJsonStr(request));
        shopBusiness.updateShopBusinessLicense(request);
    }

    @Operation(summary = "修改食品经营许可证")
    @PostMapping("update_food_business_license")
    @PreAuthorize("hasAnyAuthority('data','data_shop')")
    public void updateFoodBusinessLicense(@RequestBody @Validated OpenApiShopUpdateFoodBusinessLicenseRequestVo request) {
        log.info("修改食品经营许可证，clientId={} request={}", AppContext.getContext().getClientId(), JSONUtil.toJsonStr(request));
        shopBusiness.updateFoodBusinessLicense(request);
    }

    @Operation(summary = "修改消杀合同")
    @PostMapping("update_disinfecting_contract")
    @PreAuthorize("hasAnyAuthority('data','data_shop')")
    public void updateDisinfectingContract(@RequestBody @Validated OpenApiShopUpdateDisinfectingContractRequestVo request) {
        log.info("修改消杀合同，clientId={} request={}", AppContext.getContext().getClientId(), JSONUtil.toJsonStr(request));
        shopBusiness.updateDisinfectingContract(request);
    }

    @Operation(summary = "修改门头照")
    @PostMapping("update_shop_front_photo")
    @PreAuthorize("hasAnyAuthority('data','data_shop')")
    public void updateShopFrontPhoto(@RequestBody @Validated OpenApiShopUpdateShopFrontPhotoRequestVo request) {
        log.info("修改门头照，clientId={} request={}", AppContext.getContext().getClientId(), JSONUtil.toJsonStr(request));
        shopBusiness.updateShopFrontPhoto(request);
    }

    @Operation(summary = "修改门店基础信息")
    @PostMapping("update_base")
    @PreAuthorize("hasAnyAuthority('data','data_shop')")
    public void update(@RequestBody @Validated OpenApiShopUpdateRequestVo request) {
        log.info("修改门店基础信息，clientId={} request={}", AppContext.getContext().getClientId(), JSONUtil.toJsonStr(request));
        shopBusiness.updateBase(request);
    }

    @Operation(summary = "获取门店编码(根据门店Id)")
    @PostMapping("code_by_id")
    @PreAuthorize("hasAnyAuthority('data','data_shop')")
    public IdResponse<String> codeById(@RequestBody @Valid OpenApiShopIdRequestVo request) {
        return new IdResponse<>(shopBusiness.codeById(request));
    }

    @Operation(summary = "获取门店ID(根据门店编码)")
    @PostMapping("id_by_code")
    @PreAuthorize("hasAnyAuthority('data','data_shop')")
    public IdResponse<String> idByCode(@RequestBody @Valid OpenApiShopCodeRequestVo request) {
        return new IdResponse<>(shopBusiness.idByCode(request));
    }

    @Operation(summary = "获取门店详情")
    @PostMapping("detail")
    @PreAuthorize("hasAnyAuthority('data','data_shop')")
    public OpenApiShopDetailResponseVo detail(@RequestBody @Valid OpenApiShopIdRequestVo request) {
        return shopBusiness.detail(request);
    }

    @Operation(summary = "获取门店营业执照")
    @PostMapping("get_shop_business_license")
    @PreAuthorize("hasAnyAuthority('data','data_shop')")
    public OpenApiShopBusinessLicenseResponseVo getShopBusinessLicense(@RequestBody @Valid OpenApiShopIdRequestVo request) {
        return shopBusiness.getShopBusinessLicense(request);
    }

    @Operation(summary = "获取食品经营许可证")
    @PostMapping("get_food_business_license")
    @PreAuthorize("hasAnyAuthority('data','data_shop')")
    public OpenApiFoodBusinessLicenseResponseVo foodBusinessLicenseByCode(@RequestBody @Valid OpenApiShopIdRequestVo request) {
        return shopBusiness.getFoodBusinessLicense(request);
    }

    @Operation(summary = "获取消杀合同")
    @PostMapping("get_disinfecting_contract")
    @PreAuthorize("hasAnyAuthority('data','data_shop')")
    public OpenApiDisinfectingContractResponseVo disinfectingContractByCode(@RequestBody @Valid OpenApiShopIdRequestVo request) {
        return shopBusiness.getDisinfectingContract(request);
    }

    @Operation(summary = "获取门头照")
    @PostMapping("get_shop_front_photo")
    @PreAuthorize("hasAnyAuthority('data','data_shop')")
    public OpenApiShopFrontPhotoResponseVo shopFrontPhotoByCode(@RequestBody @Valid OpenApiShopIdRequestVo request) {
        return shopBusiness.getShopFrontPhoto(request);
    }

    @Operation(summary = "获取门店标签选项Id")
    @PostMapping("query_shop_label_optionId")
    @PreAuthorize("hasAnyAuthority('data','data_shop')")
    public List<String> queryShopLabelOption(@RequestBody @Validated OpenApiShopIdRequestVo request) {
        return shopBusiness.queryShopLabelOptionIds(request);
    }

    @Operation(summary = "获取门店列表(基础信息)")
    @PostMapping("list_simple")
    @PreAuthorize("hasAnyAuthority('data','data_shop')")
    public List<OpenApiShopListSimpleResponseVo> listSimple(@RequestBody @Valid OpenApiShopListSimpleRequestVo request) {
        return shopBusiness.listSimple(request);
    }

}