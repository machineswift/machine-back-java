package com.machine.service.data.file.material.dao;

import com.machine.service.data.file.material.dao.mapper.entity.DataMaterialCategoryEntity;

import java.util.List;
import java.util.Set;

public interface IDataMaterialCategoryDao {

    String insert(DataMaterialCategoryEntity entity);

    int delete(String id);

    int update(DataMaterialCategoryEntity entity);

    DataMaterialCategoryEntity getById(String id);

    DataMaterialCategoryEntity getByParentIdAndName(String parentId,
                                                   String name);

    List<DataMaterialCategoryEntity> selectByIdSet(Set<String> idSet);

    List<DataMaterialCategoryEntity> listAll();
}
