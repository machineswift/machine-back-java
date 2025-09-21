package com.machine.app.manage.data.material.business;

import com.machine.app.manage.data.material.controller.vo.response.DataMaterialDetailResponseVo;
import com.machine.app.manage.data.material.controller.vo.response.DataMaterialExpandListResponseVo;
import com.machine.app.manage.data.material.controller.vo.resquest.*;
import com.machine.sdk.common.envm.data.material.DataMaterIalTypeEnum;
import com.machine.sdk.common.model.request.IdRequest;
import com.machine.sdk.common.model.response.PageResponse;
import org.springframework.web.multipart.MultipartFile;

public interface IDataMaterialBusiness {

    String upload(DataMaterIalTypeEnum materIalType,
                  MultipartFile file);

    String uploadImage(int thumbnailWeight,
                       int thumbnailHeight,
                       MultipartFile file);

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
