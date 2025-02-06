package com.machine.app.manage.data.file.business.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.json.JSONUtil;
import com.machine.app.manage.data.file.business.IDownLoadCenterBusiness;
import com.machine.app.manage.data.file.controller.vo.request.DownloadFileCreateRequestVo;
import com.machine.app.manage.data.file.controller.vo.request.DownloadFilePageRequestVo;
import com.machine.app.manage.data.file.controller.vo.response.QueryDownloadFileDetailResponseVo;
import com.machine.app.manage.data.file.controller.vo.response.QueryDownloadFileListResponseVo;
import com.machine.client.data.file.IDownloadFileClient;
import com.machine.client.data.file.dto.input.CreateDownloadFileClientInputDto;
import com.machine.client.data.file.dto.input.DownloadFilePageClientInputDto;
import com.machine.client.data.file.dto.output.QueryDownloadFileListOutputDto;
import com.machine.sdk.common.model.request.IdRequest;
import com.machine.sdk.common.model.response.PageResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DownLoadCenterBusinessImpl implements IDownLoadCenterBusiness {

    @Autowired
    private IDownloadFileClient iDownloadFileClient;

    @Override
    public String create(DownloadFileCreateRequestVo inputDto) {
        return iDownloadFileClient.create(BeanUtil.copyProperties(inputDto, CreateDownloadFileClientInputDto.class));
    }

    @Override
    public void invoke(IdRequest request) {
        iDownloadFileClient.invoke(request.getId());
    }

    @Override
    public QueryDownloadFileDetailResponseVo detail(IdRequest request) {
        return BeanUtil.copyProperties(iDownloadFileClient.getById(request.getId()), QueryDownloadFileDetailResponseVo.class);
    }

    @Override
    public PageResponse<QueryDownloadFileListResponseVo> page(DownloadFilePageRequestVo inputDto) {
        PageResponse<QueryDownloadFileListOutputDto> page = iDownloadFileClient.page(
                BeanUtil.copyProperties(inputDto, DownloadFilePageClientInputDto.class));

        return new PageResponse<>(
                page.getCurrent(),
                page.getSize(),
                page.getTotal(),
                JSONUtil.toList(JSONUtil.toJsonStr(page.getRecords()), QueryDownloadFileListResponseVo.class));
    }
}