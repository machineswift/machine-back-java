package com.machine.service.data.file.attachment.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.json.JSONUtil;
import com.machine.client.data.file.attachment.dto.input.DataAttachmentCreateInputDto;
import com.machine.client.data.file.attachment.dto.output.DataAttachmentDetailOutputDto;
import com.machine.sdk.common.envm.data.file.DataAttachmentStatusEnum;
import com.machine.sdk.common.model.request.IdRequest;
import com.machine.sdk.common.model.request.IdSetRequest;
import com.machine.service.data.file.attachment.dao.IDataAttachmentDao;
import com.machine.service.data.file.attachment.dao.mapper.entity.DataAttachmentEntity;
import com.machine.service.data.file.attachment.service.IDataAttachmentService;
import com.machine.service.data.file.attachment.dao.IDataFileDao;
import com.machine.service.data.file.attachment.dao.mapper.entity.DataFileEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

@Slf4j
@Service
public class DataAttachmentServiceImpl implements IDataAttachmentService {

    @Autowired
    private IDataFileDao fileDao;

    @Autowired
    private IDataAttachmentDao attachmentDao;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public String create(DataAttachmentCreateInputDto inputDto) {
        DataFileEntity fileEntity = new DataFileEntity();
        fileEntity.setFileType(inputDto.getFileType());
        fileEntity.setOriginalName(inputDto.getOriginalName());
        fileEntity.setStorageName(inputDto.getStorageName());
        fileEntity.setMd5Hash(inputDto.getMd5Hash());
        fileEntity.setFileInfo(inputDto.getFileInfo());
        fileEntity.setSize(inputDto.getSize());
        String fileId = fileDao.insert(fileEntity);

        DataAttachmentEntity attachmentEntity = new DataAttachmentEntity();
        attachmentEntity.setStatus(DataAttachmentStatusEnum.PUBLISHED);
        attachmentEntity.setEntity(inputDto.getEntity());
        attachmentEntity.setEntityId(inputDto.getEntityId());
        attachmentEntity.setFileId(fileId);
        attachmentEntity.setExpireTime(inputDto.getExpireTime());
        return attachmentDao.insert(attachmentEntity);
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
}
