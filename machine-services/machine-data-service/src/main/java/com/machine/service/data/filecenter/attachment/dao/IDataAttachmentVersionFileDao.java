package com.machine.service.data.filecenter.attachment.dao;

import com.machine.service.data.filecenter.attachment.dao.mapper.entity.DataAttachmentVersionFileEntity;

import java.util.List;
import java.util.Set;

public interface IDataAttachmentVersionFileDao {

    String insert(DataAttachmentVersionFileEntity entity);

    int update(DataAttachmentVersionFileEntity entity);

    DataAttachmentVersionFileEntity getById(String id);

    DataAttachmentVersionFileEntity getByVersionId(String versionId);

    List<DataAttachmentVersionFileEntity> selectByIdSet(Set<String> idSet);

    List<DataAttachmentVersionFileEntity> listByVersionId(String versionId);

    List<DataAttachmentVersionFileEntity> listByVersionIdSet(Set<String> versionIdSet);

}
