package com.machine.app.openapi.data.file.business;

import com.machine.app.openapi.data.file.controller.vo.request.DownloadFileCreatFinishRequestVo;

public interface IOpenApiDownLoadFileBusiness {

    String create(DownloadFileCreatFinishRequestVo request);
}
