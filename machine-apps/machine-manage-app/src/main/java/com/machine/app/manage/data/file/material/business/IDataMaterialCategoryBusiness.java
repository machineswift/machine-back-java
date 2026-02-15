package com.machine.app.manage.data.file.material.business;

import com.machine.app.manage.data.file.material.controller.vo.response.DataMaterialCategoryDetailResponseVo;
import com.machine.app.manage.data.file.material.controller.vo.response.DataMaterialCategorySimpleTreeResponseVo;
import com.machine.app.manage.data.file.material.controller.vo.resquest.DataMaterialCategoryCreateRequestVo;
import com.machine.app.manage.data.file.material.controller.vo.resquest.DataMaterialCategoryUpdateParentRequestVo;
import com.machine.app.manage.data.file.material.controller.vo.resquest.DataMaterialCategoryUpdateRequestVo;
import com.machine.sdk.common.model.request.IdRequest;


public interface IDataMaterialCategoryBusiness {

    String create(DataMaterialCategoryCreateRequestVo request);

    void delete(IdRequest request);

    void update(DataMaterialCategoryUpdateRequestVo request);

    void updateParent(DataMaterialCategoryUpdateParentRequestVo request);

    DataMaterialCategoryDetailResponseVo detail(IdRequest request);

    DataMaterialCategorySimpleTreeResponseVo treeAllSimple();
}
