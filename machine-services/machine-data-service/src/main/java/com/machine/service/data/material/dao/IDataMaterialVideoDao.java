package com.machine.service.data.material.dao;

import com.machine.service.data.material.dao.mapper.entity.DataMaterialVideoEntity;

import java.util.List;
import java.util.Set;

public interface IDataMaterialVideoDao {

    void insert(DataMaterialVideoEntity entity);

    int deleteById(String id);

    int deleteByMaterialId(String materialId);

    DataMaterialVideoEntity getById(String id);

    DataMaterialVideoEntity getByMaterialId(String materialId);

    List<DataMaterialVideoEntity> selectByMaterialIdSet(Set<String> materialIdSet);
}
