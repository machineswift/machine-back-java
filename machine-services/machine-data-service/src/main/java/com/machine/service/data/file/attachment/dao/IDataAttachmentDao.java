package com.machine.service.data.file.attachment.dao;

import com.machine.service.data.file.attachment.dao.mapper.entity.DataAttachmentEntity;

import java.util.List;
import java.util.Set;

public interface IDataAttachmentDao {

    String insert(DataAttachmentEntity entity);

    int update(DataAttachmentEntity entity);

    DataAttachmentEntity getById(String id);

    List<DataAttachmentEntity> selectByIdSet(Set<String> idSet);

}
