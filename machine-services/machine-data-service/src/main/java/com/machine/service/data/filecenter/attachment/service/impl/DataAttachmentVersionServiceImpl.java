package com.machine.service.data.filecenter.attachment.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.json.JSONUtil;
import com.machine.client.data.filecenter.attachment.dto.DataFileTempCreateDto;
import com.machine.client.data.filecenter.attachment.dto.input.DataAttachmentVersionCreateInputDto;
import com.machine.client.data.filecenter.attachment.dto.input.DataAttachmentVersionDeleteInputDto;
import com.machine.client.data.filecenter.attachment.dto.input.DataAttachmentVersionRollbackInputDto;
import com.machine.client.data.filecenter.attachment.dto.input.DataAttachmentVersionUpdateInputDto;
import com.machine.client.data.filecenter.attachment.dto.output.DataAttachmentVersionDetailOutputDto;
import com.machine.sdk.base.envm.base.ModuleEntityEnum;
import com.machine.sdk.base.envm.data.filecenter.DataFileTypeEnum;
import com.machine.sdk.base.envm.data.filecenter.attachment.DataAttachmentChangeTypeEnum;
import com.machine.sdk.base.envm.data.filecenter.attachment.DataAttachmentStatusEnum;
import com.machine.sdk.base.exception.data.DataBusinessException;
import com.machine.sdk.base.model.request.IdSetRequest;
import com.machine.service.data.filecenter.attachment.dao.*;
import com.machine.service.data.filecenter.attachment.dao.mapper.entity.*;
import com.machine.service.data.filecenter.attachment.service.IDataAttachmentVersionService;
import com.machine.starter.obs.service.ObsFileService;
import com.machine.starter.obs.tool.ObsAttachmentPathBuilder;
import com.machine.starter.obs.validate.ModuleEntityValidatorRegistry;
import lombok.extern.slf4j.Slf4j;
import org.dromara.x.file.storage.core.FileInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

@Slf4j
@Service
public class DataAttachmentVersionServiceImpl implements IDataAttachmentVersionService {

    @Autowired
    private ObsFileService obsFileService;

    @Autowired
    private ModuleEntityValidatorRegistry moduleEntityValidatorRegistry;

    @Autowired
    private IDataFileDao fileDao;

    @Autowired
    private IDataFileTempDao dataFileTempDao;

    @Autowired
    private IDataAttachmentDao attachmentDao;

    @Autowired
    private IDataAttachmentVersionDao attachmentVersionDao;

    @Autowired
    private IDataAttachmentVersionFileDao attachmentVersionFileDao;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public String create(DataAttachmentVersionCreateInputDto inputDto) {
        List<DataFileTempCreateDto> fileTempList = inputDto.getFileTempList();

        // 校验参数
        Set<String> fileTempIdSet = fileTempList.stream()
                .map(DataFileTempCreateDto::getFileId).collect(Collectors.toSet());
        List<DataFileTempEntity> fileTempEntityList = dataFileTempDao.selectByIds(fileTempIdSet);
        if (fileTempIdSet.size() != fileTempEntityList.size()) {
            throw new DataBusinessException("data.attachmentVersion.service.create.fileTempNotExists", "临时文件不存在");
        }
        Map<String, DataFileTempEntity> fileTempEntityMap = fileTempEntityList.stream()
                .collect(Collectors.toMap(
                        DataFileTempEntity::getId,
                        Function.identity()
                ));

        DataAttachmentEntity attachmentEntity = attachmentDao.getById(inputDto.getAttachmentId());
        if (attachmentEntity == null) {
            throw new DataBusinessException("data.attachmentVersion.service.create.attachmentNotExists", "附件不存在");
        }

        // 新增附件版本管理表数据
        DataAttachmentVersionEntity versionEntity = new DataAttachmentVersionEntity();
        versionEntity.setAttachmentId(attachmentEntity.getId());
        versionEntity.setStatus(DataAttachmentStatusEnum.ENABLED);
        versionEntity.setEntity(attachmentEntity.getEntity());
        versionEntity.setEntityId(attachmentEntity.getEntityId());
        versionEntity.setAttachmentGroup(attachmentEntity.getAttachmentGroup());
        versionEntity.setVersionNo(attachmentEntity.getMaxVersionNo());
        versionEntity.setIsCurrent(1);
        versionEntity.setChangeType(DataAttachmentChangeTypeEnum.CREATE);
        versionEntity.setChangeDesc(inputDto.getChangeDesc());
        versionEntity.setChangeTime(System.currentTimeMillis());
        versionEntity.setChangeDesc(inputDto.getChangeDesc());
        String attachmentVersionId = attachmentVersionDao.insert(versionEntity);

        // 新增附件版本文件明细表数据
        ModuleEntityEnum entityEnum = versionEntity.getEntity();
        String entityId = versionEntity.getEntityId();
        String attachmentGroup = versionEntity.getAttachmentGroup();
        Integer versionNo = versionEntity.getVersionNo();
        String obsPath = new ObsAttachmentPathBuilder().forAttachment(entityEnum, entityId, attachmentGroup, versionNo);

        for (DataFileTempCreateDto tempFileItem : fileTempList) {
            DataFileTempEntity fileTempEntity = fileTempEntityMap.get(tempFileItem.getFileId());

            DataFileTypeEnum fileType = fileTempEntity.getFileType();
            FileInfo fileTempInfo = JSONUtil.toBean(fileTempEntity.getFileInfo(), FileInfo.class);

            // 上传文件（需要 SHA-256 哈希）
            FileInfo fileInfo;
            if (DataFileTypeEnum.IMAGE == fileType) {
                fileInfo = obsFileService.uploadImageWithHash(obsFileService.downloadToStream(fileTempInfo), fileTempEntity.getOriginalName(), obsPath);
            } else {
                fileInfo = obsFileService.uploadWithHash(obsFileService.downloadToStream(fileTempInfo), fileTempEntity.getOriginalName(), obsPath);
            }

            // 新增文件数据
            DataFileEntity fileEntity = new DataFileEntity();
            fileEntity.setFileType(fileType);
            fileEntity.setOriginalName(fileTempEntity.getOriginalName());
            fileEntity.setStorageName(fileInfo.getFilename());
            fileEntity.setStoragePath(fileInfo.getPath());
            fileEntity.setHashSha256(fileInfo.getHashInfo() != null ? fileInfo.getHashInfo().getSha256() : null);
            fileInfo.setHashInfo(null);
            fileEntity.setFileInfo(JSONUtil.toJsonStr(fileInfo));
            fileEntity.setSize(fileInfo.getSize());
            String fileId = fileDao.insert(fileEntity);

            // 新增附件版本文件明细数据
            DataAttachmentVersionFileEntity versionFileEntity = new DataAttachmentVersionFileEntity();
            versionFileEntity.setAttachmentVersionId(attachmentVersionId);
            versionFileEntity.setFileId(fileId);
            versionFileEntity.setSort(tempFileItem.getSort());
            versionFileEntity.setFeatures(tempFileItem.getFeatures());
            attachmentVersionFileDao.insert(versionFileEntity);
        }

        // 删除临时文件
        for (DataFileTempEntity fileTempEntity : fileTempEntityList) {
            FileInfo fileTempInfo = JSONUtil.toBean(fileTempEntity.getFileInfo(), FileInfo.class);
            obsFileService.delete(fileTempInfo);
            dataFileTempDao.deleteById(fileTempEntity.getId());
        }

        return attachmentVersionId;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(DataAttachmentVersionDeleteInputDto inputDto) {
        String attachmentId = inputDto.getAttachmentId();
        Integer targetVersionNo = inputDto.getTargetVersionNo();

        // 校验附件是否存在
        DataAttachmentEntity attachmentEntity = attachmentDao.getById(attachmentId);
        if (attachmentEntity == null) {
            throw new DataBusinessException("data.attachmentVersion.service.delete.attachmentNotExists", "附件不存在");
        }

        // 获取目标版本
        DataAttachmentVersionEntity targetVersionEntity = attachmentVersionDao.getTargetVersionNo(attachmentId, targetVersionNo);
        if (targetVersionEntity == null) {
            throw new DataBusinessException("data.attachmentVersion.service.delete.targetVersionNotExists", "目标版本不存在");
        }

        // 状态流转校验（ENABLED/LOCKED/DISABLED/EXPIRED → DELETED）
        if (!targetVersionEntity.getStatus().canTransitionTo(DataAttachmentStatusEnum.DELETED)) {
            throw new DataBusinessException("data.attachmentVersion.service.delete.statusNotAllowed", "当前状态不允许删除");
        }

        // 如果是当前版本，取消当前标记，并清空附件主表的当前版本ID
        if (targetVersionEntity.getIsCurrent() == 1) {
            targetVersionEntity.setIsCurrent(0);
            attachmentEntity.setCurrentVersionId(null);
            attachmentDao.update(attachmentEntity);
        }

        // 标记为已删除
        DataAttachmentVersionEntity updateVersionEntity = new DataAttachmentVersionEntity();
        updateVersionEntity.setId(targetVersionEntity.getId());
        updateVersionEntity.setStatus(DataAttachmentStatusEnum.DELETED);
        attachmentVersionDao.update(updateVersionEntity);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(DataAttachmentVersionUpdateInputDto inputDto) {
        ModuleEntityEnum entityEnum = inputDto.getEntity();
        String entityId = inputDto.getEntityId();
        String attachmentGroup = inputDto.getAttachmentGroup();

        // 校验参数
        moduleEntityValidatorRegistry.validateAttachmentGroup(entityEnum, attachmentGroup);
        moduleEntityValidatorRegistry.validateEntityId(entityEnum, entityId);

        // 校验临时文件参数
        List<DataFileTempCreateDto> fileTempList = inputDto.getFileTempList();
        Set<String> fileTempIdSet = fileTempList.stream()
                .map(DataFileTempCreateDto::getFileId).collect(Collectors.toSet());
        List<DataFileTempEntity> fileTempEntityList = dataFileTempDao.selectByIds(fileTempIdSet);
        if (fileTempIdSet.size() != fileTempEntityList.size()) {
            throw new DataBusinessException("data.attachmentVersion.service.update.fileTempNotExists", "临时文件不存在");
        }
        Map<String, DataFileTempEntity> fileTempEntityMap = fileTempEntityList.stream()
                .collect(Collectors.toMap(
                        DataFileTempEntity::getId,
                        Function.identity()
                ));

        // 校验附件是否存在
        DataAttachmentEntity attachmentEntity = attachmentDao.getByUk(entityEnum, entityId, attachmentGroup);
        if (attachmentEntity == null) {
            throw new DataBusinessException("data.attachmentVersion.service.update.attachmentNotExists", "附件不存在");
        }

        // 获取当前生效版本，将其标记为历史版本
        DataAttachmentVersionEntity currentVersionEntity = attachmentVersionDao.getCurrentVersion(attachmentEntity.getId());
        if (currentVersionEntity != null) {
            currentVersionEntity.setIsCurrent(0);
            attachmentVersionDao.update(currentVersionEntity);
        }

        // 创建新版本
        int newVersionNo = attachmentEntity.getMaxVersionNo() + 1;
        DataAttachmentVersionEntity versionEntity = new DataAttachmentVersionEntity();
        versionEntity.setAttachmentId(attachmentEntity.getId());
        versionEntity.setStatus(DataAttachmentStatusEnum.ENABLED);
        versionEntity.setEntity(attachmentEntity.getEntity());
        versionEntity.setEntityId(attachmentEntity.getEntityId());
        versionEntity.setAttachmentGroup(attachmentEntity.getAttachmentGroup());
        versionEntity.setVersionNo(newVersionNo);
        versionEntity.setIsCurrent(1);
        versionEntity.setChangeType(DataAttachmentChangeTypeEnum.UPDATE);
        versionEntity.setChangeDesc(inputDto.getChangeDesc());
        versionEntity.setChangeTime(System.currentTimeMillis());
        String attachmentVersionId = attachmentVersionDao.insert(versionEntity);

        // 更新附件主表：当前版本ID和最大版本号
        DataAttachmentEntity updateAttachmentEntity = new DataAttachmentEntity();
        updateAttachmentEntity.setId(attachmentEntity.getId());
        updateAttachmentEntity.setCurrentVersionId(attachmentVersionId);
        updateAttachmentEntity.setMaxVersionNo(newVersionNo);
        attachmentDao.update(updateAttachmentEntity);

        // 上传文件并创建版本文件明细
        String obsPath = new ObsAttachmentPathBuilder().forAttachment(entityEnum, entityId, attachmentGroup, newVersionNo);

        for (DataFileTempCreateDto tempFileItem : fileTempList) {
            DataFileTempEntity fileTempEntity = fileTempEntityMap.get(tempFileItem.getFileId());

            DataFileTypeEnum fileType = fileTempEntity.getFileType();
            FileInfo fileTempInfo = JSONUtil.toBean(fileTempEntity.getFileInfo(), FileInfo.class);

            // 上传到永久存储（需要 SHA-256 哈希）
            FileInfo fileInfo;
            if (DataFileTypeEnum.IMAGE == fileType) {
                fileInfo = obsFileService.uploadImageWithHash(obsFileService.downloadToStream(fileTempInfo), fileTempEntity.getOriginalName(), obsPath);
            } else {
                fileInfo = obsFileService.uploadWithHash(obsFileService.downloadToStream(fileTempInfo), fileTempEntity.getOriginalName(), obsPath);
            }

            // 新增文件主表记录
            DataFileEntity fileEntity = new DataFileEntity();
            fileEntity.setFileType(fileType);
            fileEntity.setOriginalName(fileTempEntity.getOriginalName());
            fileEntity.setStorageName(fileInfo.getFilename());
            fileEntity.setStoragePath(fileInfo.getPath());
            fileEntity.setHashSha256(fileInfo.getHashInfo() != null ? fileInfo.getHashInfo().getSha256() : null);
            fileInfo.setHashInfo(null);
            fileEntity.setFileInfo(JSONUtil.toJsonStr(fileInfo));
            fileEntity.setSize(fileInfo.getSize());
            String fileId = fileDao.insert(fileEntity);

            // 新增附件版本文件明细
            DataAttachmentVersionFileEntity versionFileEntity = new DataAttachmentVersionFileEntity();
            versionFileEntity.setAttachmentVersionId(attachmentVersionId);
            versionFileEntity.setFileId(fileId);
            versionFileEntity.setSort(tempFileItem.getSort());
            versionFileEntity.setFeatures(tempFileItem.getFeatures());
            attachmentVersionFileDao.insert(versionFileEntity);
        }

        // 删除临时文件
        for (DataFileTempEntity fileTempEntity : fileTempEntityList) {
            FileInfo fileTempInfo = JSONUtil.toBean(fileTempEntity.getFileInfo(), FileInfo.class);
            obsFileService.delete(fileTempInfo);
            dataFileTempDao.deleteById(fileTempEntity.getId());
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void rollback(DataAttachmentVersionRollbackInputDto inputDto) {
        String attachmentId = inputDto.getAttachmentId();
        Integer targetVersionNo = inputDto.getTargetVersionNo();

        // 校验附件是否存在
        DataAttachmentEntity attachmentEntity = attachmentDao.getById(attachmentId);
        if (attachmentEntity == null) {
            throw new DataBusinessException("data.attachmentVersion.service.rollback.attachmentNotExists", "附件不存在");
        }

        // 校验版本号合法性，不能回滚到当前版本或更高版本
        if (targetVersionNo >= attachmentEntity.getMaxVersionNo()) {
            throw new DataBusinessException("data.attachmentVersion.service.rollback.invalidVersionNo", "只能回滚到历史版本");
        }

        // 获取目标版本（被回滚的目标）
        DataAttachmentVersionEntity targetVersionEntity = attachmentVersionDao.getTargetVersionNo(attachmentId, targetVersionNo);
        if (null == targetVersionEntity) {
            throw new DataBusinessException("data.attachmentVersion.service.rollback.targetVersionNotExists", "目标版本不存在");
        }

        // 获取当前版本，标记为历史
        DataAttachmentVersionEntity currentVersionEntity = attachmentVersionDao.getCurrentVersion(attachmentId);
        if (currentVersionEntity != null) {
            currentVersionEntity.setIsCurrent(0);
            attachmentVersionDao.update(currentVersionEntity);
        }

        // 创建新版本（回滚版本）
        int newVersionNo = attachmentEntity.getMaxVersionNo() + 1;
        DataAttachmentVersionEntity versionEntity = new DataAttachmentVersionEntity();
        versionEntity.setAttachmentId(attachmentEntity.getId());
        versionEntity.setStatus(DataAttachmentStatusEnum.ENABLED);
        versionEntity.setEntity(attachmentEntity.getEntity());
        versionEntity.setEntityId(attachmentEntity.getEntityId());
        versionEntity.setAttachmentGroup(attachmentEntity.getAttachmentGroup());
        versionEntity.setVersionNo(newVersionNo);
        versionEntity.setIsCurrent(1);
        versionEntity.setSourceVersionId(targetVersionEntity.getId());
        versionEntity.setChangeType(DataAttachmentChangeTypeEnum.ROLLBACK);
        versionEntity.setChangeDesc("回滚到版本 " + targetVersionNo);
        versionEntity.setChangeTime(System.currentTimeMillis());
        String attachmentVersionId = attachmentVersionDao.insert(versionEntity);

        // 更新附件主表：当前版本ID和最大版本号
        DataAttachmentEntity updateAttachmentEntity = new DataAttachmentEntity();
        updateAttachmentEntity.setId(attachmentId);
        updateAttachmentEntity.setCurrentVersionId(attachmentVersionId);
        updateAttachmentEntity.setMaxVersionNo(newVersionNo);
        attachmentDao.update(updateAttachmentEntity);

        // 获取目标版本的文件列表，复制到新版本（复用已有文件，无需重新上传）
        List<DataAttachmentVersionFileEntity> targetVersionFiles = attachmentVersionFileDao.listByVersionId(targetVersionEntity.getId());
        for (DataAttachmentVersionFileEntity targetVersionFile : targetVersionFiles) {
            DataAttachmentVersionFileEntity versionFileEntity = new DataAttachmentVersionFileEntity();
            versionFileEntity.setAttachmentVersionId(attachmentVersionId);
            versionFileEntity.setFileId(targetVersionFile.getFileId());
            versionFileEntity.setSort(targetVersionFile.getSort());
            versionFileEntity.setFeatures(targetVersionFile.getFeatures());
            attachmentVersionFileDao.insert(versionFileEntity);
        }
    }

    @Override
    public DataAttachmentVersionDetailOutputDto getById(String id) {
        DataAttachmentVersionEntity versionEntity = attachmentVersionDao.getById(id);
        if (versionEntity == null) {
            return null;
        }

        return JSONUtil.toBean(JSONUtil.toJsonStr(versionEntity), DataAttachmentVersionDetailOutputDto.class);
    }

    @Override
    public List<DataAttachmentVersionDetailOutputDto> listByIdSet(IdSetRequest request) {
        List<DataAttachmentVersionEntity> versionEntityList = attachmentVersionDao.selectByIds(request.getIdSet());
        if (CollectionUtil.isEmpty(versionEntityList)) {
            return List.of();
        }

        return JSONUtil.toList(JSONUtil.toJsonStr(versionEntityList), DataAttachmentVersionDetailOutputDto.class);
    }

}
