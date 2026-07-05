package com.machine.service.data.filecenter.attachment.dao;

import com.machine.sdk.base.envm.base.ModuleEntityEnum;
import com.machine.service.data.filecenter.attachment.dao.mapper.entity.DataAttachmentEntity;

import java.util.List;
import java.util.Set;

public interface IDataAttachmentDao {

    String insert(DataAttachmentEntity entity);

    int update(DataAttachmentEntity entity);

    DataAttachmentEntity getById(String id);

    DataAttachmentEntity getByUk(ModuleEntityEnum entityEnum,
                                 String entityId,
                                 String attachmentGroup);

    List<DataAttachmentEntity> selectByIdSet(Set<String> idSet);

}
