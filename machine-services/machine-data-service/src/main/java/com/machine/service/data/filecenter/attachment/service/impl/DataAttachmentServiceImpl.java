package com.machine.service.data.filecenter.attachment.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.json.JSONUtil;
import com.machine.client.data.filecenter.attachment.dto.input.DataAttachmentCreateInputDto;
import com.machine.client.data.filecenter.attachment.dto.input.DataAttachmentVersionCreateInputDto;
import com.machine.client.data.filecenter.attachment.dto.output.DataAttachmentDetailOutputDto;
import com.machine.client.data.filecenter.attachment.dto.output.DataAttachmentWithCurrentFileInfoOutputDto;
import com.machine.sdk.base.envm.base.ModuleEntityEnum;
import com.machine.sdk.base.envm.data.filecenter.attachment.DataAttachmentStatusEnum;
import com.machine.sdk.base.model.request.IdRequest;
import com.machine.sdk.base.model.request.IdSetRequest;
import com.machine.service.data.filecenter.attachment.dao.*;
import com.machine.service.data.filecenter.attachment.dao.mapper.entity.*;
import com.machine.service.data.filecenter.attachment.service.IDataAttachmentService;
import com.machine.service.data.filecenter.attachment.service.IDataAttachmentVersionService;
import com.machine.starter.obs.validate.ModuleEntityValidatorRegistry;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import org.dromara.x.file.storage.core.FileInfo;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Service
public class DataAttachmentServiceImpl implements IDataAttachmentService {

    @Autowired
    private ModuleEntityValidatorRegistry moduleEntityValidatorRegistry;

    @Autowired
    private IDataFileDao fileDao;

    @Autowired
    private IDataAttachmentDao attachmentDao;

    @Autowired
    private IDataAttachmentVersionService dataAttachmentVersionService;

    @Autowired
    private IDataAttachmentVersionFileDao attachmentVersionFileDao;


    @Override
    @Transactional(rollbackFor = Exception.class)
    public String create(DataAttachmentCreateInputDto inputDto) {
        ModuleEntityEnum entityEnum = inputDto.getEntity();
        String entityId = inputDto.getEntityId();
        String attachmentGroup = inputDto.getAttachmentGroup();

        DataAttachmentEntity attachmentDbEntity = attachmentDao.getByUk(entityEnum, entityId, attachmentGroup);
        if (null != attachmentDbEntity) {
            return attachmentDbEntity.getId();
        }

        // 校验数据数据
        moduleEntityValidatorRegistry.validateAttachmentGroup(entityEnum, attachmentGroup);
        moduleEntityValidatorRegistry.validateEntityId(entityEnum, entityId);

        // 新增附件主表数据
        DataAttachmentEntity attachmentInsertEntity = new DataAttachmentEntity();
        attachmentInsertEntity.setStatus(DataAttachmentStatusEnum.ENABLED);
        attachmentInsertEntity.setEntity(inputDto.getEntity());
        attachmentInsertEntity.setEntityId(inputDto.getEntityId());
        attachmentInsertEntity.setAttachmentGroup(inputDto.getAttachmentGroup());
        attachmentInsertEntity.setMaxVersionNo(1);
        attachmentInsertEntity.setExpireTime(inputDto.getExpireTime());
        String attachmentId = attachmentDao.insert(attachmentInsertEntity);

        // 新增附件版本管理表数据
        DataAttachmentVersionCreateInputDto versionCreateInputDto = JSONUtil.toBean(
                JSONUtil.toJsonStr(inputDto), DataAttachmentVersionCreateInputDto.class);
        versionCreateInputDto.setAttachmentId(attachmentId);
        String attachmentVersionId = dataAttachmentVersionService.create(versionCreateInputDto);

        // 更新新增附件主表数据
        DataAttachmentEntity updateAttachment = new DataAttachmentEntity();
        updateAttachment.setId(attachmentId);
        updateAttachment.setCurrentVersionId(attachmentVersionId);
        attachmentDao.update(updateAttachment);

        return attachmentId;
    }

    @Override
    public DataAttachmentDetailOutputDto getById(IdRequest request) {
        DataAttachmentEntity dbEntity = attachmentDao.getById(request.getId());
        if (dbEntity == null) {
            return null;
        }
        return JSONUtil.toBean(JSONUtil.toJsonStr(dbEntity), DataAttachmentDetailOutputDto.class, true);
    }

    @Override
    public List<DataAttachmentDetailOutputDto> listByIdSet(IdSetRequest request) {
        Set<String> idSet = request.getIdSet();
        if (CollectionUtil.isEmpty(idSet)) {
            return List.of();
        }

        List<DataAttachmentEntity> entityList = attachmentDao.selectByIdSet(idSet);
        if (CollectionUtil.isEmpty(entityList)) {
            return List.of();
        }

        return JSONUtil.toList(JSONUtil.toJsonStr(entityList), DataAttachmentDetailOutputDto.class);
    }

    @Override
    public DataAttachmentWithCurrentFileInfoOutputDto getCurrentByAttachmentId(IdRequest request) {
        DataAttachmentEntity attachmentEntity = attachmentDao.getById(request.getId());
        if (attachmentEntity == null) {
            return null;
        }

        DataAttachmentWithCurrentFileInfoOutputDto outputDto = JSONUtil.toBean(
                JSONUtil.toJsonStr(attachmentEntity), DataAttachmentWithCurrentFileInfoOutputDto.class, true);

        // 获取当前版本的所有文件信息
        if (attachmentEntity.getCurrentVersionId() != null) {
            List<DataAttachmentVersionFileEntity> versionFileList =
                    attachmentVersionFileDao.listByVersionId(attachmentEntity.getCurrentVersionId());
            outputDto.setFileInfoList(buildFileInfoList(versionFileList));
        }

        return outputDto;
    }

    @Override
    public Map<String, DataAttachmentWithCurrentFileInfoOutputDto> mapCurrentByAttachmentIdSet(IdSetRequest request) {
        Set<String> idSet = request.getIdSet();
        if (CollectionUtil.isEmpty(idSet)) {
            return Map.of();
        }

        List<DataAttachmentEntity> entityList = attachmentDao.selectByIdSet(idSet);
        if (CollectionUtil.isEmpty(entityList)) {
            return Map.of();
        }

        // 批量获取当前版本的文件信息
        Set<String> currentVersionIdSet = entityList.stream()
                .map(DataAttachmentEntity::getCurrentVersionId)
                .filter(java.util.Objects::nonNull)
                .collect(Collectors.toSet());

        Map<String, List<DataAttachmentVersionFileEntity>> versionFileMap;
        if (CollectionUtil.isNotEmpty(currentVersionIdSet)) {
            List<DataAttachmentVersionFileEntity> allVersionFiles =
                    attachmentVersionFileDao.listByVersionIdSet(currentVersionIdSet);
            versionFileMap = allVersionFiles.stream()
                    .collect(Collectors.groupingBy(DataAttachmentVersionFileEntity::getAttachmentVersionId));
        } else {
            versionFileMap = Map.of();
        }

        // 批量获取文件信息
        Set<String> fileIdSet = versionFileMap.values().stream()
                .flatMap(List::stream)
                .map(DataAttachmentVersionFileEntity::getFileId)
                .collect(Collectors.toSet());

        Map<String, DataFileEntity> fileEntityMap;
        if (CollectionUtil.isNotEmpty(fileIdSet)) {
            List<DataFileEntity> fileEntities = fileDao.selectByIdSet(fileIdSet);
            fileEntityMap = fileEntities.stream()
                    .collect(Collectors.toMap(DataFileEntity::getId, p -> p));
        } else {
            fileEntityMap = Map.of();
        }

        // 构建返回结果
        Map<String, DataAttachmentWithCurrentFileInfoOutputDto> resultMap = new java.util.LinkedHashMap<>();
        for (DataAttachmentEntity entity : entityList) {
            DataAttachmentWithCurrentFileInfoOutputDto outputDto = JSONUtil.toBean(
                    JSONUtil.toJsonStr(entity), DataAttachmentWithCurrentFileInfoOutputDto.class, true);

            // 获取当前版本的所有文件信息
            if (entity.getCurrentVersionId() != null && versionFileMap.containsKey(entity.getCurrentVersionId())) {
                List<DataAttachmentVersionFileEntity> versionFileList = versionFileMap.get(entity.getCurrentVersionId());
                outputDto.setFileInfoList(buildFileInfoList(versionFileList, fileEntityMap));
            }

            resultMap.put(entity.getId(), outputDto);
        }

        return resultMap;
    }

    /**
     * 构建文件信息列表（单次查询，批量传入 fileEntityMap）
     */
    private List<DataAttachmentWithCurrentFileInfoOutputDto.DataFileInfo> buildFileInfoList(
            List<DataAttachmentVersionFileEntity> versionFileList,
            Map<String, DataFileEntity> fileEntityMap) {

        if (CollectionUtil.isEmpty(versionFileList)) {
            return List.of();
        }
        return versionFileList.stream()
                .map(versionFile -> buildDataFileInfo(fileEntityMap.get(versionFile.getFileId())))
                .filter(java.util.Objects::nonNull)
                .collect(Collectors.toList());
    }

    /**
     * 构建文件信息列表（独立查询每条文件记录）
     */
    private List<DataAttachmentWithCurrentFileInfoOutputDto.DataFileInfo> buildFileInfoList(
            List<DataAttachmentVersionFileEntity> versionFileList) {

        if (CollectionUtil.isEmpty(versionFileList)) {
            return List.of();
        }
        return versionFileList.stream()
                .map(versionFile -> {
                    DataFileEntity fileEntity = fileDao.getById(versionFile.getFileId());
                    return buildDataFileInfo(fileEntity);
                })
                .filter(java.util.Objects::nonNull)
                .collect(Collectors.toList());
    }

    private DataAttachmentWithCurrentFileInfoOutputDto.DataFileInfo buildDataFileInfo(DataFileEntity fileEntity) {
        if (fileEntity == null) {
            return null;
        }
        DataAttachmentWithCurrentFileInfoOutputDto.DataFileInfo fileInfo =
                new DataAttachmentWithCurrentFileInfoOutputDto.DataFileInfo();
        fileInfo.setId(fileEntity.getId());
        fileInfo.setFileType(fileEntity.getFileType());
        fileInfo.setOriginalName(fileEntity.getOriginalName());
        fileInfo.setStorageName(fileEntity.getStorageName());
        fileInfo.setHashSha256(fileEntity.getHashSha256());
        fileInfo.setFileInfo(JSONUtil.toBean(fileEntity.getFileInfo(), FileInfo.class));
        fileInfo.setSize(fileEntity.getSize());
        return fileInfo;
    }
}
