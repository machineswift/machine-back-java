package com.machine.service.hrm.department.dao.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.machine.sdk.beisen.envm.BeiSenDepartmentStatusEnum;
import com.machine.service.hrm.department.dao.IDepartmentDao;
import com.machine.service.hrm.department.dao.mapper.IDepartmentMapper;
import com.machine.service.hrm.department.dao.mapper.entity.DepartmentEntity;
import com.machine.starter.mybatis.BaseEntity;
import com.machine.starter.redis.command.CustomerRedisCommands;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static com.machine.starter.redis.constant.RedisPrefix4HrmConstant.Department.HRM_DEPARTMENT_TREE_KEY;


@Repository
public class DepartmentDaoImpl implements IDepartmentDao {

    @Autowired
    private CustomerRedisCommands customerRedisCommands;

    @Autowired
    private IDepartmentMapper departmentMapper;

    @Override
    public String insert(DepartmentEntity insertEntity) {
        departmentMapper.insert(insertEntity);
        customerRedisCommands.del(HRM_DEPARTMENT_TREE_KEY);
        return insertEntity.getId();
    }

    @Override
    public void batchInsert(List<DepartmentEntity> entityList) {
        for (DepartmentEntity entity : entityList) {
            DepartmentEntity e = departmentMapper.selectById(entity.getId());
            if (e == null) {
                departmentMapper.insert(entity);
            } else {
                departmentMapper.updateById(entity);
            }
        }
        customerRedisCommands.del(HRM_DEPARTMENT_TREE_KEY);
    }

    @Override
    public DepartmentEntity getById(String parentId) {
        return departmentMapper.selectById(parentId);
    }

    @Override
    public DepartmentEntity getByName(String parentId,
                                      String name) {
        Wrapper<DepartmentEntity> wrapper = new LambdaQueryWrapper<DepartmentEntity>()
                .eq(DepartmentEntity::getParentId, parentId)
                .eq(DepartmentEntity::getName, name);
        return departmentMapper.selectOne(wrapper);
    }

    @Override
    public List<DepartmentEntity> listAll() {
        return departmentMapper.listAll();
    }

    @Override
    public List<DepartmentEntity> listSub4Recursion(String id) {
        Wrapper<DepartmentEntity> wrapper = new LambdaQueryWrapper<DepartmentEntity>()
                .eq(DepartmentEntity::getParentId, id)
                .eq(DepartmentEntity::getStatus, BeiSenDepartmentStatusEnum.ENABLE);
        List<DepartmentEntity> entityList = departmentMapper.selectList(wrapper);
        if (CollectionUtil.isEmpty(entityList)) {
            return null;
        }

        List<DepartmentEntity> resultList = new ArrayList<>(entityList);
        List<String> currentIds = entityList.stream().map(BaseEntity::getId).toList();
        while (true) {
            wrapper = new LambdaQueryWrapper<DepartmentEntity>()
                    .in(DepartmentEntity::getParentId, currentIds)
                    .eq(DepartmentEntity::getStatus, BeiSenDepartmentStatusEnum.ENABLE);
            entityList = departmentMapper.selectList(wrapper);
            if (CollectionUtil.isEmpty(entityList)) {
                break;
            }
            currentIds = entityList.stream().map(BaseEntity::getId).toList();
            resultList.addAll(entityList);
        }
        return resultList;
    }

    @Override
    public List<DepartmentEntity> selectByIdSet(Set<String> idSet) {
        return departmentMapper.selectByIds(idSet);
    }
}
