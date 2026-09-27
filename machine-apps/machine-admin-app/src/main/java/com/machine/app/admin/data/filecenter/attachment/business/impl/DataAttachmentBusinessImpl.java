package com.machine.app.admin.data.filecenter.attachment.business.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.machine.app.admin.data.filecenter.attachment.business.IDataAttachmentBusiness;
import com.machine.client.data.filecenter.attachment.IDataAttachmentClient;
import com.machine.client.data.filecenter.attachment.dto.output.DataAttachmentWithCurrentFileInfoOutputDto;
import com.machine.client.data.filecenter.attachment.IDataFileTempClient;
import com.machine.client.data.filecenter.attachment.dto.input.DataFileTempCreateInputDto;
import com.machine.sdk.base.envm.base.ModuleEnum;
import com.machine.sdk.base.envm.base.audit.OperateSourceEnum;
import com.machine.sdk.base.envm.data.filecenter.DataFileTypeEnum;
import com.machine.sdk.base.envm.data.filecenter.attachment.DataAttachmentOperationTypeEnum;
import com.machine.sdk.base.exception.data.DataBusinessException;
import com.machine.sdk.base.model.request.IdRequest;
import com.machine.sdk.base.tool.UUIDv7;
import com.machine.starter.obs.operateLog.AttachmentOperationLogPublisher;
import com.machine.starter.obs.service.ObsFileService;
import com.machine.starter.obs.tool.TikaFileTypeDetector;
import lombok.extern.slf4j.Slf4j;
import org.dromara.x.file.storage.core.FileInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
public class DataAttachmentBusinessImpl implements IDataAttachmentBusiness {

    @Autowired
    private AttachmentOperationLogPublisher attachmentOperationLogPublisher;

    @Autowired
    private ObsFileService obsFileService;

    @Autowired
    private IDataAttachmentClient dataAttachmentClient;

    @Autowired
    private IDataFileTempClient dataFileTempClient;

    @Override
    public String uploadTemp(MultipartFile file) {
        if (file == null || file.isEmpty()) {
            throw new DataBusinessException("data.attachment.business.upload.empty", "上传文件不能为空");
        }

        // 获取文件类型
        DataFileTypeEnum fileType = TikaFileTypeDetector.getInstance().getFileType(file);

        // 上传到对象存储（临时路径）
        String obsPath = "/temp/" + java.time.LocalDate.now() + "/" + UUIDv7.generateWithoutDashes();
        FileInfo fileInfo = obsFileService.upload(file, obsPath);

        // 记录临时文件
        DataFileTempCreateInputDto inputDto = new DataFileTempCreateInputDto();
        inputDto.setFileType(fileType);
        inputDto.setOriginalName(fileInfo.getOriginalFilename());
        inputDto.setStorageName(fileInfo.getFilename());
        inputDto.setStoragePath(fileInfo.getPath());
        inputDto.setFileInfo(JSONUtil.toJsonStr(fileInfo));
        inputDto.setSize(fileInfo.getSize());
        inputDto.setExpireTime(System.currentTimeMillis() + 24 * 60 * 60 * 1000L);

        return dataFileTempClient.create(inputDto);
    }

    @Override
    public String thumbnail(String attachmentId) {
        DataAttachmentWithCurrentFileInfoOutputDto.DataFileInfo dataFileInfo = getFirstFileInfo(attachmentId);
        if (null == dataFileInfo) {
            return null;
        }
        return obsFileService.generateThPresignedUrl(dataFileInfo.getFileInfo());
    }

    @Override
    public List<String> batchThumbnail(String attachmentId) {
        List<DataAttachmentWithCurrentFileInfoOutputDto.DataFileInfo> dataFileInfoList = getValidFileInfoList(attachmentId);
        if (CollectionUtil.isEmpty(dataFileInfoList)) {
            return null;
        }

        List<String> urlList = new ArrayList<>(dataFileInfoList.size());
        for (DataAttachmentWithCurrentFileInfoOutputDto.DataFileInfo dataFileInfo : dataFileInfoList) {
            urlList.add(obsFileService.generateThPresignedUrl(dataFileInfo.getFileInfo()));
        }
        return urlList;
    }

    @Override
    public String preview(String attachmentId) {
        DataAttachmentWithCurrentFileInfoOutputDto.DataFileInfo dataFileInfo = getFirstFileInfo(attachmentId);
        if (null == dataFileInfo) {
            return null;
        }

        String url = obsFileService.generatePresignedUrl(dataFileInfo.getFileInfo(), false);
        if (StrUtil.isNotBlank(url)) {
            attachmentOperationLogPublisher.publish(attachmentId, DataAttachmentOperationTypeEnum.PREVIEW,
                    OperateSourceEnum.ADMIN_APP, ModuleEnum.DATA);
        }
        return url;
    }

    @Override
    public List<String> batchPreview(String attachmentId) {
        List<DataAttachmentWithCurrentFileInfoOutputDto.DataFileInfo> dataFileInfoList = getValidFileInfoList(attachmentId);
        if (CollectionUtil.isEmpty(dataFileInfoList)) {
            return null;
        }

        List<String> urlList = new ArrayList<>(dataFileInfoList.size());
        for (DataAttachmentWithCurrentFileInfoOutputDto.DataFileInfo dataFileInfo : dataFileInfoList) {
            urlList.add(obsFileService.generatePresignedUrl(dataFileInfo.getFileInfo(), false));
        }
        attachmentOperationLogPublisher.publish(attachmentId, DataAttachmentOperationTypeEnum.PREVIEW,
                OperateSourceEnum.ADMIN_APP, ModuleEnum.DATA);
        return urlList;
    }

    @Override
    public String download(String attachmentId) {
        DataAttachmentWithCurrentFileInfoOutputDto.DataFileInfo dataFileInfo = getFirstFileInfo(attachmentId);
        if (null == dataFileInfo) {
            return null;
        }

        String url = obsFileService.generatePresignedUrl(dataFileInfo.getFileInfo(), true);
        if (StrUtil.isNotBlank(url)) {
            attachmentOperationLogPublisher.publish(attachmentId, DataAttachmentOperationTypeEnum.DOWNLOAD,
                    OperateSourceEnum.ADMIN_APP, ModuleEnum.DATA);
        }
        return url;
    }

    @Override
    public List<String> batchDownload(String attachmentId) {
        List<DataAttachmentWithCurrentFileInfoOutputDto.DataFileInfo> dataFileInfoList = getValidFileInfoList(attachmentId);
        if (CollectionUtil.isEmpty(dataFileInfoList)) {
            return null;
        }

        List<String> urlList = new ArrayList<>(dataFileInfoList.size());
        for (DataAttachmentWithCurrentFileInfoOutputDto.DataFileInfo dataFileInfo : dataFileInfoList) {
            urlList.add(obsFileService.generatePresignedUrl(dataFileInfo.getFileInfo(), true));
        }
        attachmentOperationLogPublisher.publish(attachmentId, DataAttachmentOperationTypeEnum.DOWNLOAD,
                OperateSourceEnum.ADMIN_APP, ModuleEnum.DATA);
        return urlList;
    }

    private DataAttachmentWithCurrentFileInfoOutputDto.DataFileInfo getFirstFileInfo(String attachmentId) {
        List<DataAttachmentWithCurrentFileInfoOutputDto.DataFileInfo> fileInfoList = getValidFileInfoList(attachmentId);
        if (CollectionUtil.isEmpty(fileInfoList)) {
            return null;
        }
        return fileInfoList.getFirst();
    }

    private List<DataAttachmentWithCurrentFileInfoOutputDto.DataFileInfo> getValidFileInfoList(String attachmentId) {
        DataAttachmentWithCurrentFileInfoOutputDto attachment =
                dataAttachmentClient.getCurrentByAttachmentId(new IdRequest(attachmentId));
        if (System.currentTimeMillis() + 5 * 1000 > attachment.getExpireTime()) {
            return null;
        }
        if (CollectionUtil.isEmpty(attachment.getFileInfoList())) {
            return null;
        }
        return attachment.getFileInfoList();
    }
}
