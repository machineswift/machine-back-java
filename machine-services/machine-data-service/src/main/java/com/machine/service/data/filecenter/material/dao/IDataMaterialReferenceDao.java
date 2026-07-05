package com.machine.service.data.filecenter.material.dao;

import com.machine.service.data.filecenter.material.dao.mapper.entity.DataMaterialReferenceEntity;

import java.util.List;
import java.util.Set;

public interface IDataMaterialReferenceDao {

    String insert(DataMaterialReferenceEntity entity);

    int delete(String id);

    int deleteByMaterialId(String materialId);

    DataMaterialReferenceEntity getById(String id);

    DataMaterialReferenceEntity getByMaterialIdAndEntity(String materialId,
                                                         String entity,
                                                         String entityId);

    List<DataMaterialReferenceEntity> listByMaterialId(String materialId);

    List<DataMaterialReferenceEntity> listByMaterialIdSet(Set<String> materialIdSet);

    List<DataMaterialReferenceEntity> listByEntity(String entity,
                                                   String entityId);

    Long selectCountByEntity(String entity,
                             String entityId);
}
