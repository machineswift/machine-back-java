package com.machine.app.manage.data.shop.business;

import com.machine.app.manage.data.shop.controller.vo.request.*;
import com.machine.app.manage.data.shop.controller.vo.response.ShopDetailResponseVo;
import com.machine.app.manage.data.shop.controller.vo.response.ShopExpandListResponseVo;
import com.machine.app.manage.data.shop.controller.vo.response.ShopSimpleListResponseVo;
import com.machine.sdk.common.model.response.PageResponse;

public interface IDataShopBusiness {

    void updateStatus(ShopUpdateShopStatusRequestVo request);

    void updateAddress(DataShopUpdateAddressRequestVo request);

    void updateCertificate(ShopUpdateCertificateRequestVo request);

    void updateBase(ShopUpdateShopBaseInfoRequestVo request);

    void updateLabelOption(ShopUpdateShopLabelOptionRequestVo request);

    void batchUpdateLabelOption(ShopBatchUpdateShopLabelOptionRequestVo request);

    void bindOrganization(ShopBindOrganizationRequestVo request);

    ShopDetailResponseVo detail(ShopIdRequestVo request);

    PageResponse<ShopSimpleListResponseVo> pageSimpled(ShopQueryPageSimpleRequestVo request);

    PageResponse<ShopExpandListResponseVo> pageExpand(ShopQueryPageExpandRequestVo request);

}
