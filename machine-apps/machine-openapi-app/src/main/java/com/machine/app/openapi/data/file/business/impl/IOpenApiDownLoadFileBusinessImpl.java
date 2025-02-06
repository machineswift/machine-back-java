package com.machine.app.openapi.data.file.business.impl;

import cn.hutool.core.bean.BeanUtil;
import com.machine.app.openapi.data.file.business.IOpenApiDownLoadFileBusiness;
import com.machine.app.openapi.data.file.controller.vo.request.DownloadFileCreatFinishRequestVo;
import com.machine.client.data.file.IDownloadFileClient;
import com.machine.client.data.file.dto.input.DownloadFileCreateFinishInputDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class IOpenApiDownLoadFileBusinessImpl implements IOpenApiDownLoadFileBusiness {

    @Autowired
    private IDownloadFileClient downloadFileClient;

    @Override
    public String create(DownloadFileCreatFinishRequestVo request) {
        DownloadFileCreateFinishInputDto inputDto = BeanUtil.toBean(request, DownloadFileCreateFinishInputDto.class);
        return downloadFileClient.createFinish(inputDto);
    }
}