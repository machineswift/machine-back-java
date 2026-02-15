package com.machine.app.manage.data.file.download.business;

import com.machine.app.manage.data.file.download.controller.vo.request.DataDownloadPageRequestVo;
import com.machine.app.manage.data.file.download.controller.vo.response.DataDownloadDetailResponseVo;
import com.machine.app.manage.data.file.download.controller.vo.response.DataDownloadListResponseVo;
import com.machine.sdk.common.model.request.IdRequest;
import com.machine.sdk.common.model.response.PageResponse;

public interface IDownLoadCenterBusiness {

    void retry(IdRequest request);

    DataDownloadDetailResponseVo detail(IdRequest request);

    PageResponse<DataDownloadListResponseVo> pageExpand(DataDownloadPageRequestVo request);

}