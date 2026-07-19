package com.machine.app.admin.data.filecenter.material.business;

import com.machine.app.admin.data.filecenter.material.controller.vo.response.DataMaterialDetailResponseVo;
import com.machine.app.admin.data.filecenter.material.controller.vo.response.DataMaterialExpandListResponseVo;
import com.machine.app.admin.data.filecenter.material.controller.vo.resquest.DataMaterialCreateRequestVo;
import com.machine.app.admin.data.filecenter.material.controller.vo.resquest.DataMaterialQueryPageRequestVo;
import com.machine.app.admin.data.filecenter.material.controller.vo.resquest.DataMaterialUpdateCategoryRequestVo;
import com.machine.app.admin.data.filecenter.material.controller.vo.resquest.DataMaterialUpdateRequestVo;
import com.machine.sdk.base.model.request.IdRequest;
import com.machine.sdk.base.model.response.PageResponse;
import jakarta.servlet.http.HttpServletRequest;

public interface IDataMaterialBusiness {

    String create(DataMaterialCreateRequestVo request,
                  HttpServletRequest servletRequest);

    void update(DataMaterialUpdateRequestVo request,
                HttpServletRequest servletRequest);

    void updateCategory(DataMaterialUpdateCategoryRequestVo request);

    DataMaterialDetailResponseVo detail(IdRequest request);

    PageResponse<DataMaterialExpandListResponseVo> pageExpand(DataMaterialQueryPageRequestVo request);

    /**
     * 获取素材附件的预签名下载 URL（用于图片/视频预览）
     */
    String getDownloadUrl(IdRequest request);

}
