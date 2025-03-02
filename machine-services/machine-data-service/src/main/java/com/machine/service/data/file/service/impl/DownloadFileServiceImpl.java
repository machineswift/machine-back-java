package com.machine.service.data.file.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.crypto.digest.DigestUtil;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.machine.client.data.file.dto.input.*;
import com.machine.client.data.file.dto.output.QueryDownloadFileDetailOutputDto;
import com.machine.client.data.file.dto.output.QueryDownloadFileListOutputDto;
import com.machine.sdk.common.context.AppContext;
import com.machine.sdk.common.envm.data.download.DownLoadFileChannelEnum;
import com.machine.sdk.common.envm.data.download.ExportTaskStatusEnum;
import com.machine.sdk.common.model.dto.data.MaterialDto;
import com.machine.sdk.common.exception.iam.IamBusinessException;
import com.machine.service.data.file.dao.IDownloadFileDao;
import com.machine.service.data.file.dao.mapper.entity.DownloadFileEntity;
import com.machine.service.data.file.service.IDownloadFileService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.machine.sdk.common.constant.CommonConstant.DOWNLOAD_FILE_EXPIRATION_DAYS;

@Slf4j
@Service
public class DownloadFileServiceImpl implements IDownloadFileService {

    @Autowired
    private IDownloadFileDao downloadFileDao;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public String createFinish(DownloadFileCreateFinishInputDto inputDto) {
        MaterialDto material = inputDto.getMaterial();
        String urlMd5 = DigestUtil.md5Hex(material.getUrl());
        List<DownloadFileEntity> dbEntityList = downloadFileDao.selectByUrlMd5(urlMd5);
        if (CollectionUtil.isNotEmpty(dbEntityList)) {
            for (DownloadFileEntity dbEntity : dbEntityList) {
                String dbMaterialInfo = dbEntity.getMaterial();
                MaterialDto dbMaterial = JSONUtil.toBean(dbMaterialInfo, MaterialDto.class);
                if (dbMaterial.getUrl().equals(material.getUrl())) {
                    throw new IamBusinessException("data.downloadFile.createFinish.fileAlreadyExists", "文件已经存在");
                }
            }
        }

        DownloadFileEntity entity = new DownloadFileEntity();
        entity.setChannel(inputDto.getChannel());
        entity.setStatus(ExportTaskStatusEnum.FINISH);
        entity.setUserId(inputDto.getUserId());
        entity.setFileName(material.getName());
        entity.setFileType(material.getType());
        entity.setUrlMd5(urlMd5);
        entity.setMaterial(JSONUtil.toJsonStr(material));
        entity.setExpirationIn(inputDto.getExpirationIn());
        return downloadFileDao.insert(entity);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public String createTask(DownloadFileContentDto inputDto) {
        DownloadFileEntity insertEntity = BeanUtil.copyProperties(inputDto, DownloadFileEntity.class);
        insertEntity.setChannel(DownLoadFileChannelEnum.SYSTEM);
        insertEntity.setStatus(ExportTaskStatusEnum.READY);
        insertEntity.setUserId(AppContext.getContext().getUserId());
        insertEntity.setContent(JSONUtil.toJsonStr(inputDto));
        insertEntity.setExpirationIn(System.currentTimeMillis() + DOWNLOAD_FILE_EXPIRATION_DAYS * 24 * 60 * 60 * 1000L);
        return downloadFileDao.insert(insertEntity);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int updateById(DownloadFileUpdateInputDto inputDto) {
        DownloadFileEntity dbEntity = downloadFileDao.getById(inputDto.getId());
        if (null == dbEntity) {
            return 0;
        }

        DownloadFileEntity downloadFileEntity = new DownloadFileEntity();
        downloadFileEntity.setId(inputDto.getId());
        downloadFileEntity.setStatus(inputDto.getStatus());

        //附件信息
        if (null != inputDto.getMaterial()) {
            MaterialDto material = inputDto.getMaterial();
            downloadFileEntity.setFileName(material.getName());
            downloadFileEntity.setFileType(material.getType());
            downloadFileEntity.setUrlMd5(DigestUtil.md5Hex(material.getUrl()));
            downloadFileEntity.setMaterial(JSONUtil.toJsonStr(material));
        }

        //内容
        String content = dbEntity.getContent();
        DownloadFileContentDto contentDto = JSONUtil.toBean(content, DownloadFileContentDto.class);
        contentDto.setUsageCount(inputDto.getUsageCount());
        downloadFileEntity.setContent(JSONUtil.toJsonStr(contentDto));

        downloadFileEntity.setFailCause(inputDto.getFailCause());
        return downloadFileDao.update(downloadFileEntity);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void invoke(String id) {
        DownloadFileEntity dbEntity = downloadFileDao.getById(id);
        if (dbEntity == null) {
            throw new IamBusinessException("data.downloadFile.invoke.taskNotExists", "任务不存在");
        }

        if (ExportTaskStatusEnum.READY == dbEntity.getStatus() ||
                ExportTaskStatusEnum.RUNNING == dbEntity.getStatus()) {
            return;
        }

        if (ExportTaskStatusEnum.FINISH == dbEntity.getStatus()) {
            throw new IamBusinessException("data.downloadFile.invoke.taskHasFinish", "任务已经完成");
        }

        if (ExportTaskStatusEnum.DEAD == dbEntity.getStatus()) {
            throw new IamBusinessException("data.downloadFile.invoke.taskHasDead", "任务已经死亡");
        }

        DownloadFileEntity updateEntity = new DownloadFileEntity();
        updateEntity.setId(id);
        updateEntity.setStatus(ExportTaskStatusEnum.READY);
        downloadFileDao.update(updateEntity);
    }

    @Override
    public QueryDownloadFileDetailOutputDto getById(String id) {
        DownloadFileEntity dbEntity = downloadFileDao.getById(id);
        if (null == dbEntity) {
            return null;
        }
        return JSONUtil.toBean(JSONUtil.toJsonStr(dbEntity), QueryDownloadFileDetailOutputDto.class);
    }

    @Override
    public List<QueryDownloadFileDetailOutputDto> queryByLimit(QueryDownloadFileQueryInputDto inputDto) {
        List<DownloadFileEntity> entityList = downloadFileDao.queryByLimit(inputDto);
        if (CollectionUtil.isEmpty(entityList)) {
            return List.of();
        }
        return JSONUtil.toList(JSONUtil.toJsonStr(entityList), QueryDownloadFileDetailOutputDto.class);
    }

    @Override
    public Page<QueryDownloadFileListOutputDto> page(DownloadFileQueryPageInputDto inputDto) {
        Page<DownloadFileEntity> page = downloadFileDao.page(inputDto);
        Page<QueryDownloadFileListOutputDto> pageResult = new Page<>(page.getCurrent(), page.getSize(), page.getTotal());
        pageResult.setRecords(JSONUtil.toList(JSONUtil.toJsonStr(page.getRecords()), QueryDownloadFileListOutputDto.class));
        return pageResult;

    }
}