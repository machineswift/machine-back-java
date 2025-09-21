package com.machine.service.hrm.department.dao;

import com.machine.service.hrm.department.dao.mapper.entity.DepartmentEntity;

import java.util.List;
import java.util.Set;

public interface IDepartmentDao {

    String insert(DepartmentEntity insertEntity);

    void batchInsert(List<DepartmentEntity> entityList);

    DepartmentEntity getById(String parentId);

    DepartmentEntity getByName(String parentId,
                               String name);

    List<DepartmentEntity> listAll();

    List<DepartmentEntity> listSub4Recursion(String id);

    List<DepartmentEntity> selectByIdSet(Set<String> idSet);
}
