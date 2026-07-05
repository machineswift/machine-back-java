package com.machine.service.data.filecenter.attachment.dao;

import com.machine.sdk.base.envm.base.ModuleEntityEnum;
import com.machine.service.data.filecenter.attachment.dao.mapper.entity.DataAttachmentVersionEntity;

import java.util.Collection;
import java.util.List;

public interface IDataAttachmentVersionDao {

    String insert(DataAttachmentVersionEntity entity);

    int update(DataAttachmentVersionEntity entity);

    DataAttachmentVersionEntity getById(String id);

    DataAttachmentVersionEntity getCurrentVersion(String attachmentId);

    DataAttachmentVersionEntity getCurrentVersion(ModuleEntityEnum entityEnum,
                                                  String entityId,
                                                  String attachmentGroup);

    DataAttachmentVersionEntity getTargetVersionNo(String attachmentId,
                                                   Integer targetVersionNo);

    List<DataAttachmentVersionEntity> selectByIds(Collection<String> ids);

}
