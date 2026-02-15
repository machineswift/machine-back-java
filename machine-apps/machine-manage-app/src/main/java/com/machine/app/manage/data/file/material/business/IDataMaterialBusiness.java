package com.machine.app.manage.data.file.material.business;

import com.machine.app.manage.data.file.material.controller.vo.response.DataMaterialDetailResponseVo;
import com.machine.app.manage.data.file.material.controller.vo.response.DataMaterialExpandListResponseVo;
import com.machine.app.manage.data.file.material.controller.vo.resquest.DataMaterialCreateRequestVo;
import com.machine.app.manage.data.file.material.controller.vo.resquest.DataMaterialQueryPageRequestVo;
import com.machine.app.manage.data.file.material.controller.vo.resquest.DataMaterialUpdateRequestVo;
import com.machine.sdk.common.model.request.IdRequest;
import com.machine.sdk.common.model.response.PageResponse;

public interface IDataMaterialBusiness {

    void create(DataMaterialCreateRequestVo request);

    void update(DataMaterialUpdateRequestVo request);

    String getPresignedUrl(String materialId);

    String getPresignedUrl(String materialId,
                           int expireSecond);

    String getThumbnailUrl(String materialId);

    String getThumbnailUrl(String materialId,
                           int expireSecond);

    DataMaterialDetailResponseVo detail(IdRequest request);

    PageResponse<DataMaterialExpandListResponseVo> pageExpand(DataMaterialQueryPageRequestVo request);

}
