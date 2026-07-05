package com.machine.app.manage.data.filecenter.attachment.business.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.json.JSONUtil;
import com.machine.app.manage.data.filecenter.attachment.business.IDataAttachmentBusiness;
import com.machine.client.data.filecenter.attachment.IDataAttachmentClient;
import com.machine.client.data.filecenter.attachment.dto.output.DataAttachmentWithCurrentFileInfoOutputDto;
import com.machine.client.data.filecenter.attachment.IDataFileTempClient;
import com.machine.client.data.filecenter.attachment.dto.input.DataFileTempCreateInputDto;
import com.machine.sdk.base.envm.data.filecenter.DataFileTypeEnum;
import com.machine.sdk.base.exception.data.DataBusinessException;
import com.machine.sdk.base.model.request.IdRequest;
import com.machine.sdk.base.tool.UUIDv7;
import com.machine.starter.obs.service.ObsFileService;
import com.machine.starter.obs.tool.TikaFileTypeDetector;
import lombok.extern.slf4j.Slf4j;
import org.dromara.x.file.storage.core.FileInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@Component
public class DataAttachmentBusinessImpl implements IDataAttachmentBusiness {

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
    public String getThumbnailUrl(String attachmentId,
                                  int expireSecond) {
        DataAttachmentWithCurrentFileInfoOutputDto attachment = dataAttachmentClient.getCurrentByAttachmentId(new IdRequest(attachmentId));
        if (System.currentTimeMillis() + 5 * 1000 > attachment.getExpireTime()) {
            return null;
        }

        if (CollectionUtil.isEmpty(attachment.getFileInfoList())) {
            return null;
        }

        // 这只处理分组有一个文件的场景
        DataAttachmentWithCurrentFileInfoOutputDto.DataFileInfo dataFileInfo = attachment.getFileInfoList().getFirst();
       return  obsFileService.generateThPresignedUrl(dataFileInfo.getFileInfo());
    }

}
