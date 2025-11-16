package com.machine.app.manage.data.tag.business;

import com.machine.app.manage.data.tag.controller.vo.request.*;
import com.machine.app.manage.data.tag.controller.vo.response.DataTagCategoryDetailResponseVo;
import com.machine.client.data.tag.dto.output.DataTagCategoryTreeSimpleOutputDto;
import com.machine.sdk.common.model.request.IdRequest;

public interface IDataTagCategoryBusiness {

    String create(DataTagCategoryCreateRequestVo request);

    void delete(IdRequest request);

    void update(DataTagCategoryUpdateRequestVo request);

    void updateSort(DataTagCategoryUpdateSortRequestVo request);

    void updateParent(DataTagCategoryUpdateParentRequestVo request);

    DataTagCategoryDetailResponseVo detail(IdRequest request);

    DataTagCategoryTreeSimpleOutputDto treeSimple(DataTagCategoryTreeRequestVo request);

}

