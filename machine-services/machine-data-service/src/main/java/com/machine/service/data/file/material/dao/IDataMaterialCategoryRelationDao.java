package com.machine.service.data.file.material.dao;

import com.machine.service.data.file.material.dao.mapper.entity.DataMaterialCategoryRelationEntity;

import java.util.List;
import java.util.Set;

public interface IDataMaterialCategoryRelationDao {

    Long selectCountByCategoryId(String categoryId);

    Long selectCountByCategoryIdSet(Set<String> categoryIdSet);

    void insert(DataMaterialCategoryRelationEntity entity);

    void deleteByMaterialId(String materialId);

    List<DataMaterialCategoryRelationEntity> listByMaterialId(String materialId);

    List<DataMaterialCategoryRelationEntity> listByCategoryIdSet(Set<String> categoryIdSet);

    List<DataMaterialCategoryRelationEntity> listByMaterialIdSet( Set<String> materialIdSet);

}
