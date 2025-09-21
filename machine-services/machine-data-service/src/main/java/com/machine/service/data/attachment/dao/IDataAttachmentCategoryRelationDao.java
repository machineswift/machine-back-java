package com.machine.service.data.attachment.dao;

import com.machine.service.data.attachment.dao.mapper.entity.DataAttachmentCategoryRelationEntity;

import java.util.List;
import java.util.Set;

public interface IDataAttachmentCategoryRelationDao {

    Long selectCountByCategoryId(String categoryId);

    Long selectCountByCategoryIdSet(Set<String> categoryIdSet);

    void insert(DataAttachmentCategoryRelationEntity entity);

    void deleteByAttachmentId(String attachmentId);

    List<DataAttachmentCategoryRelationEntity> listByAttachmentId(String attachmentId);

    List<DataAttachmentCategoryRelationEntity> listByCategoryIdSet(Set<String> categoryIdSet);

    List<DataAttachmentCategoryRelationEntity> listByAttachmentIdSet( Set<String> attachmentIdSet);

}
