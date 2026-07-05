package com.machine.app.manage.data.filecenter.download.business.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.io.IoUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.machine.app.manage.data.filecenter.download.business.IDownLoadCenterBusiness;
import com.machine.app.manage.data.filecenter.download.controller.vo.request.DataDownloadPageRequestVo;
import com.machine.app.manage.data.filecenter.download.controller.vo.response.DataDownloadDetailResponseVo;
import com.machine.app.manage.data.filecenter.download.controller.vo.response.DataDownloadListResponseVo;
import com.machine.client.data.filecenter.attachment.IDataAttachmentClient;
import com.machine.client.data.filecenter.attachment.dto.output.DataAttachmentWithCurrentFileInfoOutputDto;
import com.machine.client.data.filecenter.download.IDataDownloadClient;
import com.machine.client.data.filecenter.download.dto.input.DataDownloadQueryPageInputDto;
import com.machine.client.data.filecenter.download.dto.output.DataDownloadDetailOutputDto;
import com.machine.client.data.filecenter.download.dto.output.DataDownloadListOutputDto;
import com.machine.client.iam.user.IIamUserClient;
import com.machine.client.iam.user.dto.output.IamUserDetailOutputDto;
import com.machine.sdk.base.envm.data.filecenter.DataDownloadStatusEnum;
import com.machine.sdk.base.exception.data.DataBusinessException;
import com.machine.sdk.base.model.request.IdRequest;
import com.machine.sdk.base.model.request.IdSetRequest;
import com.machine.sdk.base.model.response.PageResponse;
import com.machine.starter.obs.service.ObsFileService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.dromara.x.file.storage.core.FileInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
public class DownLoadCenterBusinessImpl implements IDownLoadCenterBusiness {

    @Autowired
    private IIamUserClient userClient;

    @Autowired
    private IDataAttachmentClient dataAttachmentClient;

    @Autowired
    private IDataDownloadClient dataDownloadClient;

    @Autowired
    private ObsFileService obsFileService;

    @Override
    public void retry(IdRequest request) {
        dataDownloadClient.retry(request.getId());
    }

    @Override
    public DataDownloadDetailResponseVo detail(IdRequest request) {
        DataDownloadDetailOutputDto outputDto = dataDownloadClient.getById(request);
        if (outputDto == null) {
            return null;
        }

        DataDownloadDetailResponseVo responseVo = JSONUtil.toBean(JSONUtil.toJsonStr(outputDto), DataDownloadDetailResponseVo.class);
        if (StrUtil.isNotBlank(outputDto.getAttachmentId())) {
            DataAttachmentWithCurrentFileInfoOutputDto attachmentWithFileInfo = dataAttachmentClient.getCurrentByAttachmentId(new IdRequest(outputDto.getAttachmentId()));
            if (attachmentWithFileInfo != null) {
                List<DataAttachmentWithCurrentFileInfoOutputDto.DataFileInfo> fileInfoList = attachmentWithFileInfo.getFileInfoList();
                if (CollectionUtil.isNotEmpty(fileInfoList)) {
                    DataAttachmentWithCurrentFileInfoOutputDto.DataFileInfo dataFileInfo = fileInfoList.getFirst();

                    responseVo.setAttachmentId(outputDto.getAttachmentId());
                    responseVo.setFileType(dataFileInfo.getFileType());
                    responseVo.setAttachmentOriginalName(dataFileInfo.getOriginalName());
                    responseVo.setAttachmentSize(dataFileInfo.getSize());
                }
            }
        }
        return responseVo;
    }

    @Override
    public PageResponse<DataDownloadListResponseVo> pageExpand(DataDownloadPageRequestVo inputDto) {
        PageResponse<DataDownloadListOutputDto> page = dataDownloadClient.selectPage(
                BeanUtil.copyProperties(inputDto, DataDownloadQueryPageInputDto.class));

        PageResponse<DataDownloadListResponseVo> pageResponse = new PageResponse<>(
                page.getCurrent(),
                page.getSize(),
                page.getTotal());
        if (CollectionUtil.isEmpty(page.getRecords())) {
            return pageResponse;
        }

        // 查询附件数据
        Set<String> attachmentIdSet = page.getRecords().stream()
                .map(DataDownloadListOutputDto::getAttachmentId)
                .filter(StrUtil::isNotBlank)
                .collect(Collectors.toSet());
        Map<String, DataAttachmentWithCurrentFileInfoOutputDto> attachmentWithFileInfoMap = new HashMap<>();
        if (CollectionUtil.isNotEmpty(attachmentIdSet)) {
            attachmentWithFileInfoMap = dataAttachmentClient.mapCurrentByAttachmentIdSet(new IdSetRequest(attachmentIdSet));
        }

        List<DataDownloadListResponseVo> responseVoList = new ArrayList<>();
        for (DataDownloadListOutputDto outputDto : page.getRecords()) {
            String attachmentId = outputDto.getAttachmentId();
            DataDownloadListResponseVo responseVo = JSONUtil.toBean(JSONUtil.toJsonStr(outputDto), DataDownloadListResponseVo.class);

            if (StrUtil.isNotBlank(outputDto.getAttachmentId())) {
                DataAttachmentWithCurrentFileInfoOutputDto attachmentWithFileInfo = attachmentWithFileInfoMap.get(attachmentId);
                if (attachmentWithFileInfo != null) {
                    List<DataAttachmentWithCurrentFileInfoOutputDto.DataFileInfo> fileInfoList = attachmentWithFileInfo.getFileInfoList();
                    if (CollectionUtil.isNotEmpty(fileInfoList)) {
                        DataAttachmentWithCurrentFileInfoOutputDto.DataFileInfo dataFileInfo = fileInfoList.getFirst();
                        responseVo.setAttachmentId(attachmentId);
                        responseVo.setFileType(dataFileInfo.getFileType());
                        responseVo.setAttachmentOriginalName(dataFileInfo.getOriginalName());
                        responseVo.setAttachmentSize(dataFileInfo.getSize());
                    }

                    if (DataDownloadStatusEnum.FINISH == responseVo.getStatus()) {
                        responseVo.setExpireTime(attachmentWithFileInfo.getExpireTime());
                    }
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

    @Override
    public void downloadFile(IdRequest request, HttpServletResponse response) {
        // 查询下载记录
        DataDownloadDetailOutputDto downloadDetail = dataDownloadClient.getById(request);
        if (downloadDetail == null) {
            throw new DataBusinessException("data.download.business.downloadFile.notFound", "下载记录不存在");
        }
        String attachmentId = downloadDetail.getAttachmentId();
        if (StrUtil.isBlank(attachmentId)) {
            throw new DataBusinessException("data.download.business.downloadFile.attachmentNotFound", "附件ID不存在");
        }

        // 查询附件文件信息
        DataAttachmentWithCurrentFileInfoOutputDto attachment = dataAttachmentClient.getCurrentByAttachmentId(new IdRequest(attachmentId));
        if (attachment == null) {
            throw new DataBusinessException("data.download.business.downloadFile.attachmentNotFound", "附件不存在");
        }
        if (CollectionUtil.isEmpty(attachment.getFileInfoList())) {
            throw new DataBusinessException("data.download.business.downloadFile.fileNotFound", "附件文件不存在");
        }

        DataAttachmentWithCurrentFileInfoOutputDto.DataFileInfo dataFileInfo = attachment.getFileInfoList().getFirst();
        FileInfo fileInfo = dataFileInfo.getFileInfo();
        String originalName = dataFileInfo.getOriginalName();
        Long fileSize = dataFileInfo.getSize();

        // 从 MinIO 下载并返回文件流
        response.setContentType("application/octet-stream");
        response.setHeader("Content-Disposition",
                "attachment;filename=" + URLEncoder.encode(originalName, StandardCharsets.UTF_8));
        if (fileSize != null) {
            response.setContentLengthLong(fileSize);
        }

        try (InputStream inputStream = obsFileService.downloadToStream(fileInfo);
             OutputStream outputStream = response.getOutputStream()) {
            IoUtil.copy(inputStream, outputStream);
            outputStream.flush();
        } catch (Exception e) {
            log.error("下载文件异常，downloadId={}, attachmentId={}", request.getId(), attachmentId, e);
            throw new DataBusinessException("data.download.business.downloadFile.failed", "文件下载失败");
        }
    }
}