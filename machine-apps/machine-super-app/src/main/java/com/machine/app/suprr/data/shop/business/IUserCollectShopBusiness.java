package com.machine.app.suprr.data.shop.business;

import com.machine.app.suprr.data.shop.controller.vo.request.*;
import com.machine.app.suprr.data.shop.controller.vo.response.SuperShopListSimpleResponseVo;
import com.machine.sdk.common.model.response.PageResponse;

public interface IUserCollectShopBusiness {

    void collectShop(SuperShopCollectRequestVo request);

    void collectShopByCondition(SuperShopCollectByConditionRequestVo request);

    Integer number(SuperShopNumberRequestVo request);

    PageResponse<SuperShopListSimpleResponseVo> pageCollectShop(SuperShopListCollectShopRequestVo request);

    PageResponse<SuperShopListSimpleResponseVo> pageSelfShop(SuperShopPageSelfShopRequestVo request);

}
