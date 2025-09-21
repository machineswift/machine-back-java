package com.machine.service.data.material.dao;

import com.machine.service.data.material.dao.mapper.entity.DataMaterialImageEntity;

import java.util.List;
import java.util.Set;

public interface IDataMaterialImageDao {

    void insert(DataMaterialImageEntity entity);

    int deleteById(String id);

    int deleteByMaterialId(String materialId);

    DataMaterialImageEntity getById(String id);

    DataMaterialImageEntity getByMaterialId(String materialId);

    List<DataMaterialImageEntity> selectByMaterialIdSet(Set<String> materialIdSet);
}
