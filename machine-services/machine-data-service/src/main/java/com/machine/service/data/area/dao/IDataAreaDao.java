package com.machine.service.data.area.dao;

import com.machine.service.data.area.dao.mapper.entity.DataAreaEntity;

import java.util.List;
import java.util.Set;

public interface IDataAreaDao {

    String insert(DataAreaEntity entity);

    int delete(String id);

    int update(DataAreaEntity entity);

    int updateParent(String id,
                     String parentId);

    DataAreaEntity getById(String id);

    DataAreaEntity getByCode(String code);

    DataAreaEntity getByParentIdAndName(String parentId,
                                        String name);

    DataAreaEntity findRootById(String id);

    List<DataAreaEntity> selectByParentId(String parentId);

    List<DataAreaEntity> selectByParentIdSet(Set<String> parentIdSet);

    List<DataAreaEntity> findAllChildrenBFS(String code);

}
