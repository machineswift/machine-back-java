package com.machine.app.manage.data.supplier.business;

import com.machine.app.manage.data.supplier.controller.vo.request.*;
import com.machine.app.manage.data.supplier.controller.vo.response.DataSupplierCompanyDetailResponseVo;
import com.machine.app.manage.data.supplier.controller.vo.response.DataSupplierCompanyExpandListResponseVo;
import com.machine.app.manage.data.supplier.controller.vo.response.DataSupplierCompanySimpleListResponseVo;
import com.machine.sdk.common.model.request.IdRequest;
import com.machine.sdk.common.model.response.PageResponse;

public interface IDataSupplierCompanyBusiness {

    String create(DataSupplierCompanyCreateRequestVo request);

    void updateStatus(DataSupplierCompanyUpdateStatusRequestVo request);

    void update(DataSupplierCompanyUpdateRequestVo request);

    DataSupplierCompanyDetailResponseVo detail(IdRequest request);
    
    PageResponse<DataSupplierCompanySimpleListResponseVo> pageSimple(DataSupplierCompanyQueryPageSimpleRequestVo request);

    PageResponse<DataSupplierCompanyExpandListResponseVo> pageExpand(DataSupplierCompanyQueryPageExpandRequestVo request);

}
