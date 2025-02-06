package com.machine.app.manage.data.shop.controller;

import cn.hutool.json.JSONUtil;
import com.machine.app.manage.data.shop.business.IDataShopLabelBusiness;
import com.machine.app.manage.data.shop.controller.vo.request.*;
import com.machine.app.manage.data.shop.controller.vo.response.ShopLabelDetailResponseVo;
import com.machine.app.manage.data.shop.controller.vo.response.ShopLabelExpandListResponseVo;
import com.machine.app.manage.data.shop.controller.vo.response.ShopLabelSimpleListResponseVo;
import com.machine.sdk.common.model.request.IdRequest;
import com.machine.sdk.common.model.response.IdResponse;
import com.machine.sdk.common.model.response.PageResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Slf4j
@Tag(name = "【DATA】门店标签模块")
@RestController
@RequestMapping("manage/data/shop_label")
public class DataShopLabelController {

    @Autowired
    private IDataShopLabelBusiness shopLabelBusiness;

    @Operation(summary = "创建")
    @PostMapping("create")
    public IdResponse<String> create(@RequestBody @Validated  ShopLabelCreateRequestVo request) {
        log.info("创建门店标签，request={}", JSONUtil.toJsonStr(request));
        return new IdResponse<>(shopLabelBusiness.create(request));
    }

    @Operation(summary = "删除")
    @PostMapping("delete")
    public void delete(@RequestBody @Validated IdRequest request) {
        log.info("删除门店标签，request={}", JSONUtil.toJsonStr(request));
        shopLabelBusiness.delete(request);
    }

    @Operation(summary = "修改")
    @PostMapping("update")
    public void update(@RequestBody @Validated ShopLabelUpdateRequestVo request) {
        log.info("修改门店标签，request={}", JSONUtil.toJsonStr(request));
        shopLabelBusiness.update(request);
    }

    @Operation(summary = "修改状态")
    @PostMapping("update_status")
    public void updateStatus(@RequestBody @Validated ShopLabelUpdateStatusRequestVo request) {
        log.info("修改门店标签状态，request={}", JSONUtil.toJsonStr(request));
        shopLabelBusiness.updateStatus(request);
    }

    @Operation(summary = "详情")
    @PostMapping("detail")
    public ShopLabelDetailResponseVo detail(@RequestBody @Validated  IdRequest request) {
        return shopLabelBusiness.detail(request);
    }

    @Operation(summary = "分页查询(应用于组件弹窗)")
    @PostMapping("page_simple")
    public PageResponse<ShopLabelSimpleListResponseVo> pageSimple(@RequestBody @Validated ShopLabelQueryPageSimpleRequestVo request) {
        return shopLabelBusiness.pageSimple(request);
    }

    @Operation(summary = "分页查询(扩充，应用于组织管理菜单)")
    @PostMapping("page_expand")
    public PageResponse<ShopLabelExpandListResponseVo> pageExpand(@RequestBody @Validated ShopLabelQueryPageExpandRequestVo request) {
        return shopLabelBusiness.pageExpand(request);
    }

}