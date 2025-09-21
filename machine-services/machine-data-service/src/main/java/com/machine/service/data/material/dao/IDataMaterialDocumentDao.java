package com.machine.service.data.material.dao;

import com.machine.service.data.material.dao.mapper.entity.DataMaterialDocumentEntity;

import java.util.List;
import java.util.Set;

public interface IDataMaterialDocumentDao {

    void insert(DataMaterialDocumentEntity entity);

    int deleteById(String id);

    int deleteByMaterialId(String materialId);

    DataMaterialDocumentEntity getById(String id);

    DataMaterialDocumentEntity getByMaterialId(String materialId);

    List<DataMaterialDocumentEntity> selectByMaterialIdSet(Set<String> materialIdSet);
}
