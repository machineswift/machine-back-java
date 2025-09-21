package com.machine.service.data.attachment.dao;

import com.machine.service.data.attachment.dao.mapper.entity.DataAttachmentCategoryEntity;

import java.util.List;
import java.util.Set;

public interface IDataAttachmentCategoryDao {

    String insert(DataAttachmentCategoryEntity entity);

    int delete(String id);

    int update(DataAttachmentCategoryEntity entity);

    DataAttachmentCategoryEntity getById(String id);

    DataAttachmentCategoryEntity getByParentIdAndName(String parentId,
                                                      String name);

    List<DataAttachmentCategoryEntity> selectByIdSet(Set<String> idSet);

    List<DataAttachmentCategoryEntity> listAll();
}
