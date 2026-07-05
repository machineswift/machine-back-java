package com.machine.service.hrm.department.dao.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.machine.service.hrm.department.dao.IDepartmentExpansionDao;
import com.machine.service.hrm.department.dao.mapper.IDepartmentExpansionMapper;
import com.machine.service.hrm.department.dao.mapper.entity.DepartmentExpansionEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public class IDepartmentExpansionDaoImpl implements IDepartmentExpansionDao {

    @Autowired
    private IDepartmentExpansionMapper departmentExpansionMapper;

    @Override
    public String insert(DepartmentExpansionEntity insertEntity) {
        departmentExpansionMapper.insert(insertEntity);
        return insertEntity.getId();
    }

    @Override
    public void batchInsert(List<DepartmentExpansionEntity> entityList) {
        for (DepartmentExpansionEntity entity : entityList) {
            DepartmentExpansionEntity e = departmentExpansionMapper.selectById(entity.getId());
            if (e == null) {
                departmentExpansionMapper.insert(entity);
            } else {
                departmentExpansionMapper.updateById(entity);
            }
        }
    }

    @Override
    public DepartmentExpansionEntity getByDepartmentId(String departmentId) {
        Wrapper<DepartmentExpansionEntity> wrapper = new LambdaQueryWrapper<DepartmentExpansionEntity>()
                .eq(DepartmentExpansionEntity::getDepartmentId, departmentId);
        return departmentExpansionMapper.selectOne(wrapper);
    }

    @Override
    public String updateByDepartmentId(DepartmentExpansionEntity updateEntity) {
        Wrapper<DepartmentExpansionEntity> wrapper = new LambdaQueryWrapper<DepartmentExpansionEntity>()
                .eq(DepartmentExpansionEntity::getDepartmentId, updateEntity.getDepartmentId());
        int count = departmentExpansionMapper.update(updateEntity, wrapper);
        return count + "";
    }

    @Override
    public List<DepartmentExpansionEntity> listDepartmentExpansionByDepartmentIdSet(Set<String> idSet) {
        Wrapper<DepartmentExpansionEntity> wrapper = new LambdaQueryWrapper<DepartmentExpansionEntity>()
                .in (DepartmentExpansionEntity::getDepartmentId, idSet);
        return departmentExpansionMapper.selectList(wrapper);
    }
}
