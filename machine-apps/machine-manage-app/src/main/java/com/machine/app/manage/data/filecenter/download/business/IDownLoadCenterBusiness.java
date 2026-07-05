package com.machine.app.manage.data.filecenter.download.business;

import com.machine.app.manage.data.filecenter.download.controller.vo.request.DataDownloadPageRequestVo;
import com.machine.app.manage.data.filecenter.download.controller.vo.response.DataDownloadDetailResponseVo;
import com.machine.app.manage.data.filecenter.download.controller.vo.response.DataDownloadListResponseVo;
import com.machine.sdk.base.model.request.IdRequest;
import com.machine.sdk.base.model.response.PageResponse;
import jakarta.servlet.http.HttpServletResponse;

public interface IDownLoadCenterBusiness {

    void retry(IdRequest request);

    DataDownloadDetailResponseVo detail(IdRequest request);

    PageResponse<DataDownloadListResponseVo> pageExpand(DataDownloadPageRequestVo request);

    void downloadFile(IdRequest request, HttpServletResponse response);

}