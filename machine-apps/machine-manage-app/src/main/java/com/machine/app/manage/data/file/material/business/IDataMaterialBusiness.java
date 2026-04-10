package com.machine.app.manage.data.file.material.business;

import com.machine.app.manage.data.file.material.controller.vo.response.DataMaterialDetailResponseVo;
import com.machine.app.manage.data.file.material.controller.vo.response.DataMaterialExpandListResponseVo;
import com.machine.app.manage.data.file.material.controller.vo.resquest.DataMaterialCreateRequestVo;
import com.machine.app.manage.data.file.material.controller.vo.resquest.DataMaterialQueryPageRequestVo;
import com.machine.app.manage.data.file.material.controller.vo.resquest.DataMaterialUpdateCategoryRequestVo;
import com.machine.app.manage.data.file.material.controller.vo.resquest.DataMaterialUpdateRequestVo;
import com.machine.sdk.base.model.request.IdRequest;
import com.machine.sdk.base.model.response.PageResponse;

public interface IDataMaterialBusiness {

    String create(DataMaterialCreateRequestVo request);

    void update(DataMaterialUpdateRequestVo request);

    void updateCategory(DataMaterialUpdateCategoryRequestVo request);

    DataMaterialDetailResponseVo detail(IdRequest request);

    PageResponse<DataMaterialExpandListResponseVo> pageExpand(DataMaterialQueryPageRequestVo request);

}
