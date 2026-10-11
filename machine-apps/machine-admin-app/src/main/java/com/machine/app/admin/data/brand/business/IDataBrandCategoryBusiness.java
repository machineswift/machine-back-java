package com.machine.app.admin.data.brand.business;

import com.machine.app.admin.data.brand.controller.vo.request.DataBrandCategoryCreateRequestVo;
import com.machine.app.admin.data.brand.controller.vo.request.DataBrandCategoryUpdateParentRequestVo;
import com.machine.app.admin.data.brand.controller.vo.request.DataBrandCategoryUpdateRequestVo;
import com.machine.app.admin.data.brand.controller.vo.response.DataBrandCategoryDetailResponseVo;
import com.machine.app.admin.data.brand.controller.vo.response.DataBrandCategorySimpleTreeResponseVo;
import com.machine.sdk.base.model.request.IdRequest;

public interface IDataBrandCategoryBusiness {

    String create(DataBrandCategoryCreateRequestVo request);

    void delete(IdRequest request);

    void update(DataBrandCategoryUpdateRequestVo request);

    void updateParent(DataBrandCategoryUpdateParentRequestVo request);

    DataBrandCategoryDetailResponseVo detail(IdRequest request);

    DataBrandCategorySimpleTreeResponseVo treeAllSimple();
}
