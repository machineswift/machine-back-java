package com.machine.app.suprr.data.shop.controller;

import cn.hutool.json.JSONUtil;
import com.machine.app.suprr.data.shop.business.IUserCollectShopBusiness;
import com.machine.app.suprr.data.shop.controller.vo.request.*;
import com.machine.app.suprr.data.shop.controller.vo.response.SuperShopListSimpleResponseVo;
import com.machine.sdk.common.model.response.PageResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@Tag(name = "【DATA】门店模块")
@RestController
@RequestMapping("super/data/shop")
public class SuperShopController {

    @Autowired
    private IUserCollectShopBusiness userCollectShopBusiness;

    @Operation(summary = "收藏门店")
    @PostMapping("collect_shop")
    public void collectShop(@RequestBody @Validated SuperShopCollectRequestVo request) {
        log.info("收藏门店，request={}", JSONUtil.toJsonStr(request));
        userCollectShopBusiness.collectShop(request);
    }

    @Operation(summary = "收藏门店(根据条件)")
    @PostMapping("collect_shop_by_condition")
    public void collectShopByCondition(@RequestBody @Validated SuperShopCollectByConditionRequestVo request) {
        log.info("收藏门店(根据条件)，request={}", JSONUtil.toJsonStr(request));
        userCollectShopBusiness.collectShopByCondition(request);
    }

    @Operation(summary = "当前用户拥有的门店数量")
    @PostMapping("number")
    public Integer number(@RequestBody @Validated SuperShopNumberRequestVo request) {
        return userCollectShopBusiness.number(request);
    }

    @Operation(summary = "分页查询收藏的门店")
    @PostMapping("page_collect_shop")
    public PageResponse<SuperShopListSimpleResponseVo> pageCollectShop(@RequestBody @Validated SuperShopListCollectShopRequestVo request) {
        return userCollectShopBusiness.pageCollectShop(request);
    }

    @Operation(summary = "分页查询当前用户拥有的门店")
    @PostMapping("page_self_shop")
    public PageResponse<SuperShopListSimpleResponseVo> pageSelfShop(@RequestBody @Validated SuperShopPageSelfShopRequestVo request) {
        return userCollectShopBusiness.pageSelfShop(request);
    }

}
