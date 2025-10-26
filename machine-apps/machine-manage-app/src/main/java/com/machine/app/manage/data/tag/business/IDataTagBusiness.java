package com.machine.app.manage.data.tag.business;

import com.machine.app.manage.data.tag.controller.vo.request.DataTagCreateRequestVo;
import com.machine.app.manage.data.tag.controller.vo.request.DataTagQueryPageRequestVo;
import com.machine.app.manage.data.tag.controller.vo.request.DataTagUpdateCategoryRequestVo;
import com.machine.app.manage.data.tag.controller.vo.request.DataTagUpdateRequestVo;
import com.machine.app.manage.data.tag.controller.vo.response.DataTagDetailResponseVo;
import com.machine.app.manage.data.tag.controller.vo.response.DataTagExpandListResponseVo;
import com.machine.app.manage.data.tag.controller.vo.response.DataTagSimpleListResponseVo;
import com.machine.sdk.common.model.request.IdRequest;
import com.machine.sdk.common.model.response.PageResponse;

public interface IDataTagBusiness {

    String create(DataTagCreateRequestVo request);

    void delete(IdRequest request);

    void update(DataTagUpdateRequestVo request);

    void updateCategory(DataTagUpdateCategoryRequestVo request);

    DataTagDetailResponseVo detail(IdRequest request);

    PageResponse<DataTagSimpleListResponseVo> pageSimple(DataTagQueryPageRequestVo request);

    PageResponse<DataTagExpandListResponseVo> pageExpand(DataTagQueryPageRequestVo request);

}
