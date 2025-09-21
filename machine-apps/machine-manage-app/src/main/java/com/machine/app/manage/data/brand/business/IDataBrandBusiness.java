package com.machine.app.manage.data.brand.business;

import com.machine.app.manage.data.brand.controller.vo.request.DataBrandCreateRequestVo;
import com.machine.app.manage.data.brand.controller.vo.request.DataBrandQueryPageRequestVo;
import com.machine.app.manage.data.brand.controller.vo.request.DataBrandUpdateRequestVo;
import com.machine.app.manage.data.brand.controller.vo.request.DataBrandUpdateStatusRequestVo;
import com.machine.app.manage.data.brand.controller.vo.response.DataBrandDetailResponseVo;
import com.machine.app.manage.data.brand.controller.vo.response.DataBrandExpandListResponseVo;
import com.machine.app.manage.data.brand.controller.vo.response.DataBrandSimpleListResponseVo;
import com.machine.sdk.common.model.request.IdRequest;
import com.machine.sdk.common.model.response.PageResponse;

public interface IDataBrandBusiness {

    String create(DataBrandCreateRequestVo request);

    void delete(IdRequest request);
    
    void update(DataBrandUpdateRequestVo request);


    void updateStatus(DataBrandUpdateStatusRequestVo request);
    
    DataBrandDetailResponseVo detail(IdRequest request);

    PageResponse<DataBrandSimpleListResponseVo> pageSimple(DataBrandQueryPageRequestVo request);

    PageResponse<DataBrandExpandListResponseVo> pageExpand(DataBrandQueryPageRequestVo request);
}
