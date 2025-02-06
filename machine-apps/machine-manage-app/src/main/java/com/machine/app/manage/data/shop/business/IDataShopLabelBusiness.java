package com.machine.app.manage.data.shop.business;

import com.machine.app.manage.data.shop.controller.vo.request.*;
import com.machine.app.manage.data.shop.controller.vo.response.ShopLabelDetailResponseVo;
import com.machine.app.manage.data.shop.controller.vo.response.ShopLabelExpandListResponseVo;
import com.machine.app.manage.data.shop.controller.vo.response.ShopLabelSimpleListResponseVo;
import com.machine.sdk.common.model.request.IdRequest;
import com.machine.sdk.common.model.response.PageResponse;

import java.util.List;

public interface IDataShopLabelBusiness {
    String create(ShopLabelCreateRequestVo request);

    void delete(IdRequest request);

    void update(ShopLabelUpdateRequestVo request);

    void updateStatus(ShopLabelUpdateStatusRequestVo request);

    ShopLabelDetailResponseVo detail(IdRequest request);

    PageResponse<ShopLabelSimpleListResponseVo> pageSimple(ShopLabelQueryPageSimpleRequestVo request);

    PageResponse<ShopLabelExpandListResponseVo> pageExpand(ShopLabelQueryPageExpandRequestVo request);

}
