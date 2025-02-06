package com.machine.app.manage.data.shop.controller;

import cn.hutool.json.JSONUtil;
import com.machine.app.manage.data.shop.business.IDataShopBusiness;
import com.machine.app.manage.data.shop.controller.vo.request.*;
import com.machine.app.manage.data.shop.controller.vo.response.ShopDetailResponseVo;
import com.machine.app.manage.data.shop.controller.vo.response.ShopExpandListResponseVo;
import com.machine.app.manage.data.shop.controller.vo.response.ShopSimpleListResponseVo;
import com.machine.sdk.common.model.response.PageResponse;
import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Slf4j
@Tag(name = "【DATA】门店模块")
@RestController
@RequestMapping("manage/data/shop")
public class DataShopController {

    @Autowired
    private IDataShopBusiness shopBusiness;

    @Hidden
    @Operation(summary = "修改门店状态")
    @PostMapping("update_status")
    public void updateStatus(@RequestBody @Validated ShopUpdateShopStatusRequestVo request) {
        log.info("修改门店状态，request={}", JSONUtil.toJsonStr(request));
        shopBusiness.updateStatus(request);
    }

    @Operation(summary = "修改门店地址信息")
    @PostMapping("update_address")
    public void updateAddress(@RequestBody @Validated DataShopUpdateAddressRequestVo request) {
        log.info("修改门店地址信息，request={}", JSONUtil.toJsonStr(request));
        shopBusiness.updateAddress(request);
    }

    @Operation(summary = "修改门店证件")
    @PostMapping("update_certificate")
    public void updateCertificate(@RequestBody @Validated ShopUpdateCertificateRequestVo request) {
        log.info("修改门店证件，request={}", JSONUtil.toJsonStr(request));
        shopBusiness.updateCertificate(request);
    }

    @Hidden
    @Operation(summary = "修改门店基础信息")
    @PostMapping("update_base")
    public void updateBase(@RequestBody @Validated ShopUpdateShopBaseInfoRequestVo request) {
        log.info("修改门店基础信息，request={}", JSONUtil.toJsonStr(request));
        shopBusiness.updateBase(request);
    }

    @Operation(summary = "修改门店标签选项")
    @PostMapping("update_label_option")
    public void updateLabelOption(@RequestBody @Validated ShopUpdateShopLabelOptionRequestVo request) {
        log.info("修改门店标签选项，request={}", JSONUtil.toJsonStr(request));
        shopBusiness.updateLabelOption(request);
    }

    @Operation(summary = "批量修改门店标签选项")
    @PostMapping("batch_update_label_option")
    public void batchUpdateLabelOption(@RequestBody @Validated ShopBatchUpdateShopLabelOptionRequestVo request) {
        log.info("批量修改门店标签选项，request={}", JSONUtil.toJsonStr(request));
        shopBusiness.batchUpdateLabelOption(request);
    }

    @Operation(summary = "门店绑定组织")
    @PostMapping("bind_organization")
    public void bindOrganization(@RequestBody @Validated ShopBindOrganizationRequestVo request) {
        log.info("门店绑定组织，request={}", JSONUtil.toJsonStr(request));
        shopBusiness.bindOrganization(request);
    }

    @Operation(summary = "查询门店详情")
    @PostMapping("detail")
    public ShopDetailResponseVo detail(@RequestBody @Valid ShopIdRequestVo request) {
        return shopBusiness.detail(request);
    }

    @Operation(summary = "分页查询(应用于组件弹窗)")
    @PostMapping("page_simpled")
    public PageResponse<ShopSimpleListResponseVo> pageSimpled(@RequestBody @Validated ShopQueryPageSimpleRequestVo request) {
        return shopBusiness.pageSimpled(request);
    }

    @Operation(summary = "分页查询(应用于门店菜单)")
    @PostMapping("page_expand")
    public PageResponse<ShopExpandListResponseVo> pageExpand(@RequestBody @Validated ShopQueryPageExpandRequestVo request) {
        return shopBusiness.pageExpand(request);
    }

}