package com.machine.app.admin.data.brand.business;

import com.machine.app.admin.data.brand.controller.vo.request.*;
import com.machine.app.admin.data.brand.controller.vo.response.DataBrandDetailResponseVo;
import com.machine.app.admin.data.brand.controller.vo.response.DataBrandExpandListResponseVo;
import com.machine.app.admin.data.brand.controller.vo.response.DataBrandSimpleListResponseVo;
import com.machine.sdk.base.model.request.IdRequest;
import com.machine.sdk.base.model.response.PageResponse;

public interface IDataBrandBusiness {

    String create(DataBrandCreateRequestVo request);

    void delete(IdRequest request);
    
    void update(DataBrandUpdateRequestVo request);


    void updateStatus(DataBrandUpdateStatusRequestVo request);

    void updateParent(DataBrandUpdateParentIdRequestVo request);
    
    DataBrandDetailResponseVo detail(IdRequest request);

    PageResponse<DataBrandSimpleListResponseVo> childrenSimple(DataBrandQueryChildrenRequestVo request);

    PageResponse<DataBrandExpandListResponseVo> childrenExpand(DataBrandQueryChildrenRequestVo request);

    PageResponse<DataBrandSimpleListResponseVo> pageSimple(DataBrandQuerySimplePageRequestVo request);

    PageResponse<DataBrandExpandListResponseVo> pageExpand(DataBrandQueryPageRequestVo request);
}
