package com.machine.service.data.material.dao;

import com.machine.service.data.material.dao.mapper.entity.DataMaterialFileEntity;

import java.util.List;
import java.util.Set;

public interface IDataMaterialFileDao {

    void insert(DataMaterialFileEntity entity);

    int deleteById(String id);

    int deleteByMaterialId(String materialId);

    DataMaterialFileEntity getById(String id);

    DataMaterialFileEntity getByMaterialId(String materialId);

    List<DataMaterialFileEntity> selectByMaterialIdSet(Set<String> materialIdSet);
}
