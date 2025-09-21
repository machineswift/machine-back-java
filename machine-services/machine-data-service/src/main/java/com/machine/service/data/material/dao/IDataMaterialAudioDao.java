package com.machine.service.data.material.dao;

import com.machine.service.data.material.dao.mapper.entity.DataMaterialAudioEntity;

import java.util.List;
import java.util.Set;

public interface IDataMaterialAudioDao {

    void insert(DataMaterialAudioEntity entity);

    int deleteById(String id);

    int deleteByMaterialId(String materialId);

    DataMaterialAudioEntity getById(String id);

    DataMaterialAudioEntity getByMaterialId(String materialId);

    List<DataMaterialAudioEntity> selectByMaterialIdSet(Set<String> materialIdSet);
}
