package com.machine.service.data.material.dao;

import com.machine.service.data.material.dao.mapper.entity.DataMaterialTextEntity;

import java.util.List;
import java.util.Set;

public interface IDataMaterialTextDao {

    void insert(DataMaterialTextEntity entity);

    int deleteById(String id);

    int deleteByMaterialId(String materialId);

    DataMaterialTextEntity getById(String id);

    DataMaterialTextEntity getByMaterialId(String materialId);

    List<DataMaterialTextEntity> selectByMaterialIdSet(Set<String> materialIdSet);

}
