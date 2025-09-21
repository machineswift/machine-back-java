package com.machine.app.manage.data.file.business;

import com.machine.app.manage.data.file.controller.vo.request.DataDownloadPageRequestVo;
import com.machine.app.manage.data.file.controller.vo.response.DataDownloadDetailResponseVo;
import com.machine.app.manage.data.file.controller.vo.response.DataDownloadListResponseVo;
import com.machine.sdk.common.model.request.IdRequest;
import com.machine.sdk.common.model.response.PageResponse;

public interface IDownLoadCenterBusiness {

    void retry(IdRequest request);

    DataDownloadDetailResponseVo detail(IdRequest request);

    PageResponse<DataDownloadListResponseVo> pageExpand(DataDownloadPageRequestVo request);

}