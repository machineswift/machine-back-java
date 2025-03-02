package com.machine.app.manage.data.file.business.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.machine.app.manage.data.file.business.IDownLoadCenterBusiness;
import com.machine.app.manage.data.file.controller.vo.request.DownloadFilePageRequestVo;
import com.machine.app.manage.data.file.controller.vo.response.QueryDownloadFileDetailResponseVo;
import com.machine.app.manage.data.file.controller.vo.response.QueryDownloadFileListResponseVo;
import com.machine.client.data.file.IDownloadFileClient;
import com.machine.client.data.file.dto.input.DownloadFileQueryPageInputDto;
import com.machine.client.data.file.dto.output.QueryDownloadFileDetailOutputDto;
import com.machine.client.data.file.dto.output.QueryDownloadFileListOutputDto;
import com.machine.sdk.common.model.dto.data.MaterialDto;
import com.machine.sdk.common.model.request.IdRequest;
import com.machine.sdk.common.model.response.PageResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class DownLoadCenterBusinessImpl implements IDownLoadCenterBusiness {

    @Autowired
    private IDownloadFileClient downloadFileClient;

    @Override
    public void scheduleTask(IdRequest request) {
        downloadFileClient.scheduleTask(request.getId());
    }

    @Override
    public QueryDownloadFileDetailResponseVo detail(IdRequest request) {
        QueryDownloadFileDetailOutputDto outputDto = downloadFileClient.getById(request.getId());
        if (outputDto == null) {
            return null;
        }

        QueryDownloadFileDetailResponseVo responseVo = JSONUtil.toBean(JSONUtil.toJsonStr(outputDto), QueryDownloadFileDetailResponseVo.class);
        if (StrUtil.isNotBlank(outputDto.getMaterial())) {
            responseVo.setMaterial(JSONUtil.toBean(JSONUtil.toJsonStr(outputDto.getMaterial()), MaterialDto.class));
        }
        return responseVo;
    }

    @Override
    public PageResponse<QueryDownloadFileListResponseVo> page(DownloadFilePageRequestVo inputDto) {
        PageResponse<QueryDownloadFileListOutputDto> page = downloadFileClient.page(
                BeanUtil.copyProperties(inputDto, DownloadFileQueryPageInputDto.class));

        PageResponse<QueryDownloadFileListResponseVo> pageResponse = new PageResponse<>(
                page.getCurrent(),
                page.getSize(),
                page.getTotal());
        if (CollectionUtil.isEmpty(page.getRecords())) {
            return pageResponse;
        }

        List<QueryDownloadFileListResponseVo> responseVoList = new ArrayList<>();
        for (QueryDownloadFileListOutputDto outputDto : page.getRecords()) {
            QueryDownloadFileListResponseVo responseVo = JSONUtil.toBean(JSONUtil.toJsonStr(outputDto), QueryDownloadFileListResponseVo.class);
            responseVo.setMaterial(JSONUtil.toBean(JSONUtil.toJsonStr(outputDto.getMaterial()), MaterialDto.class));
            responseVoList.add(responseVo);
        }
        pageResponse.setRecords(responseVoList);

        return pageResponse;
    }
}