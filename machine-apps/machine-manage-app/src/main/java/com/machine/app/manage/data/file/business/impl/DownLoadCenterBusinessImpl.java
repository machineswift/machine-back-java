package com.machine.app.manage.data.file.business.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.machine.app.manage.data.file.business.IDownLoadCenterBusiness;
import com.machine.app.manage.data.file.controller.vo.request.DataDownloadPageRequestVo;
import com.machine.app.manage.data.file.controller.vo.response.DataDownloadDetailResponseVo;
import com.machine.app.manage.data.file.controller.vo.response.DataDownloadListResponseVo;
import com.machine.app.manage.data.shop.controller.vo.response.DataShopExpandListResponseVo;
import com.machine.client.data.attachment.IDataAttachmentClient;
import com.machine.client.data.attachment.dto.DataAttachmentDto;
import com.machine.client.data.attachment.dto.output.DataAttachmentDetailOutputDto;
import com.machine.client.data.file.IDataDownloadClient;
import com.machine.client.data.file.dto.input.DataDownloadQueryPageInputDto;
import com.machine.client.data.file.dto.output.DataDownloadDetailOutputDto;
import com.machine.client.data.file.dto.output.DataDownloadListOutputDto;
import com.machine.client.iam.user.IIamUserClient;
import com.machine.client.iam.user.dto.output.IamUserDetailOutputDto;
import com.machine.sdk.common.model.request.IdRequest;
import com.machine.sdk.common.model.request.IdSetRequest;
import com.machine.sdk.common.model.response.PageResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class DownLoadCenterBusinessImpl implements IDownLoadCenterBusiness {

    @Autowired
    private IIamUserClient userClient;

    @Autowired
    private IDataAttachmentClient attachmentClient;

    @Autowired
    private IDataDownloadClient downloadClient;

    @Override
    public void retry(IdRequest request) {
        downloadClient.retry(request.getId());
    }

    @Override
    public DataDownloadDetailResponseVo detail(IdRequest request) {
        DataDownloadDetailOutputDto outputDto = downloadClient.getById(request.getId());
        if (outputDto == null) {
            return null;
        }

        DataDownloadDetailResponseVo responseVo = JSONUtil.toBean(JSONUtil.toJsonStr(outputDto), DataDownloadDetailResponseVo.class);
        if (StrUtil.isNotBlank(outputDto.getAttachmentId())) {
            DataAttachmentDetailOutputDto attachmentOutputDto = attachmentClient.getById(new IdRequest(outputDto.getAttachmentId()));
            if (attachmentOutputDto != null) {
                responseVo.setAttachment(JSONUtil.toBean(JSONUtil.toJsonStr(attachmentOutputDto), DataAttachmentDto.class));
            }
        }
        return responseVo;
    }

    @Override
    public PageResponse<DataDownloadListResponseVo> pageExpand(DataDownloadPageRequestVo inputDto) {
        PageResponse<DataDownloadListOutputDto> page = downloadClient.selectPage(
                BeanUtil.copyProperties(inputDto, DataDownloadQueryPageInputDto.class));

        PageResponse<DataDownloadListResponseVo> pageResponse = new PageResponse<>(
                page.getCurrent(),
                page.getSize(),
                page.getTotal());
        if (CollectionUtil.isEmpty(page.getRecords())) {
            return pageResponse;
        }

        //附件ID
        Set<String> attachmentIdSet = page.getRecords().stream()
                .map(DataDownloadListOutputDto::getAttachmentId)
                .filter(StrUtil::isNotBlank)
                .collect(Collectors.toSet());

        Map<String, DataAttachmentDetailOutputDto> attachmentMap = new HashMap<>();
        if (CollectionUtil.isNotEmpty(attachmentIdSet)) {
            attachmentMap = attachmentClient.mapByIdSet(new IdSetRequest(attachmentIdSet));
        }

        List<DataDownloadListResponseVo> responseVoList = new ArrayList<>();
        for (DataDownloadListOutputDto outputDto : page.getRecords()) {
            DataDownloadListResponseVo responseVo = JSONUtil.toBean(JSONUtil.toJsonStr(outputDto), DataDownloadListResponseVo.class);

            if (StrUtil.isNotBlank(outputDto.getAttachmentId())) {
                DataAttachmentDetailOutputDto attachmentOutputDto = attachmentMap.get(outputDto.getAttachmentId());
                if (attachmentOutputDto != null) {
                    responseVo.setAttachment(JSONUtil.toBean(JSONUtil.toJsonStr(attachmentOutputDto), DataAttachmentDto.class));
                }
            }
            responseVoList.add(responseVo);
        }
        pageResponse.setRecords(responseVoList);

        {//创建人、修改人姓名
            Set<String> userIdSet = pageResponse.getRecords().stream().map(DataDownloadListResponseVo::getCreateBy).collect(Collectors.toSet());
            userIdSet.addAll(pageResponse.getRecords().stream().map(DataDownloadListResponseVo::getUpdateBy).collect(Collectors.toSet()));
            Map<String, IamUserDetailOutputDto> userSimpleDetailMap = userClient.mapByIdSet(new IdSetRequest(userIdSet));
            for (DataDownloadListResponseVo vo : pageResponse.getRecords()) {
                vo.setCreateName(userSimpleDetailMap.get(vo.getCreateBy()).getName());
                vo.setUpdateName(userSimpleDetailMap.get(vo.getUpdateBy()).getName());
            }
        }

        return pageResponse;
    }
}