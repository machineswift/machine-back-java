package com.machine.app.manage.data.tag.business;

import com.machine.app.manage.data.tag.controller.vo.request.*;
import com.machine.app.manage.data.tag.controller.vo.response.DataTagDetailResponseVo;
import com.machine.app.manage.data.tag.controller.vo.response.DataTagExpandListResponseVo;
import com.machine.app.manage.data.tag.controller.vo.response.DataTagSimpleListResponseVo;
import com.machine.sdk.common.model.request.IdRequest;
import com.machine.sdk.common.model.response.PageResponse;

public interface IDataTagBusiness {

    String create(DataTagCreateRequestVo request);

    void delete(IdRequest request);

    void update(DataTagUpdateRequestVo request);

    void updateCode(DataTagUpdateCodeRequestVo request);

    void updateStatus(DataTagUpdateStatusRequestVo request);

    void updateSort(DataTagUpdateSortRequestVo request);

    void updateCategory(DataTagUpdateCategoryRequestVo request);

    DataTagDetailResponseVo detail(IdRequest request);

    PageResponse<DataTagSimpleListResponseVo> pageSimple(DataTagQueryPageRequestVo request);

    PageResponse<DataTagExpandListResponseVo> pageExpand(DataTagQueryPageRequestVo request);

}
