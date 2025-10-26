package com.machine.service.data.tag.dao;

import com.machine.sdk.common.envm.data.tag.ProfileSubjectTypeEnum;
import com.machine.service.data.tag.dao.mapper.entity.DataTagCategoryEntity;

import java.util.List;

public interface IDataTagCategoryDao {

    String insert(DataTagCategoryEntity entity);

    int delete(String id);

    int update(DataTagCategoryEntity entity);

    int updateParentId(String id,
                       String parentId);

    DataTagCategoryEntity getById(String id);

    DataTagCategoryEntity getByParentIdAndName(String parentId,
                                               String name);

    List<DataTagCategoryEntity> listAllByType(ProfileSubjectTypeEnum type);
}
