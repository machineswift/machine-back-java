package com.machine.app.manage.data.file.business;

import com.machine.app.manage.data.file.controller.vo.request.DownloadFileCreateRequestVo;
import com.machine.app.manage.data.file.controller.vo.request.DownloadFilePageRequestVo;
import com.machine.app.manage.data.file.controller.vo.response.QueryDownloadFileDetailResponseVo;
import com.machine.app.manage.data.file.controller.vo.response.QueryDownloadFileListResponseVo;
import com.machine.sdk.common.model.request.IdRequest;
import com.machine.sdk.common.model.response.PageResponse;

public interface IDownLoadCenterBusiness {

    String create(DownloadFileCreateRequestVo request);

    void invoke(IdRequest request);

    QueryDownloadFileDetailResponseVo detail(IdRequest request);

    PageResponse<QueryDownloadFileListResponseVo> page(DownloadFilePageRequestVo request);

}