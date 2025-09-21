package com.machine.app.manage.data.shop.business;

import com.machine.app.manage.data.shop.controller.vo.request.*;
import com.machine.app.manage.data.shop.controller.vo.response.DataShopCertificateResponseVo;
import com.machine.app.manage.data.shop.controller.vo.response.DataShopDetailResponseVo;
import com.machine.app.manage.data.shop.controller.vo.response.DataShopExpandListResponseVo;
import com.machine.app.manage.data.shop.controller.vo.response.DataShopSimpleListResponseVo;
import com.machine.sdk.common.model.request.IdRequest;
import com.machine.sdk.common.model.response.PageResponse;

public interface IDataShopBusiness {

    String create(DataShopCreateRequestVo request);

    void update(DataShopUpdateRequestVo request);

    void updateBusinessStatus(DataShopUpdateShopBusinessStatusRequestVo request);

    void updateOperationStatus(DataShopUpdateShopOperationStatusRequestVo request);

    void updatePhysicalStatus(DataShopUpdateShopPhysicalStatusRequestVo request);

    void updateCertificate(DataShopUpdateCertificateRequestVo request);

    void updateLabelOption(DataShopUpdateShopLabelOptionRequestVo request);

    void batchUpdateLabelOption(DataShopBatchUpdateShopLabelOptionRequestVo request);

    void bindOrganization(DataShopBindOrganizationRequestVo request);

    DataShopDetailResponseVo detail(IdRequest request);

    DataShopCertificateResponseVo getCertificate(IdRequest request);

    PageResponse<DataShopSimpleListResponseVo> pageSimple(DataShopQueryPageRequestVo request);

    PageResponse<DataShopExpandListResponseVo> pageExpand(DataShopQueryPageRequestVo request);

}
