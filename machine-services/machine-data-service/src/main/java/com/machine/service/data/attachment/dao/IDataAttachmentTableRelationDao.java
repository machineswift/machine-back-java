package com.machine.service.data.attachment.dao;

import com.machine.sdk.common.envm.system.SystemTableNameEnum;
import com.machine.service.data.attachment.dao.mapper.entity.DataAttachmentTableRelationEntity;

public interface IDataAttachmentTableRelationDao {

    void insert(DataAttachmentTableRelationEntity entity);

    DataAttachmentTableRelationEntity selectByUk(String attachmentId,
                                                 SystemTableNameEnum tableName,
                                                 String dataId);
}
