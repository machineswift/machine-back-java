package com.machine.service.hrm.department.dao;

import com.machine.service.hrm.department.dao.mapper.entity.DepartmentExpansionEntity;

import java.util.List;
import java.util.Set;

public interface IDepartmentExpansionDao {

    String insert(DepartmentExpansionEntity insertEntity);

    void batchInsert(List<DepartmentExpansionEntity> entityList);

    DepartmentExpansionEntity getByDepartmentId(String departmentId);

    String updateByDepartmentId(DepartmentExpansionEntity updateEntity);

    List<DepartmentExpansionEntity> listDepartmentExpansionByDepartmentIdSet( Set<String> idSet);
}
