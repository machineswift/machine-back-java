package com.machine.service.hrm.employee.dao.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.machine.client.hrm.employee.dto.input.HrmEmployeeQueryIListInputDto;
import com.machine.service.hrm.employee.dao.IEmployeeDao;
import com.machine.service.hrm.employee.dao.mapper.IEmployeeMapper;
import com.machine.service.hrm.employee.dao.mapper.entity.EmployeeEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public class EmployeeDaoImpl implements IEmployeeDao {

    @Autowired
    private IEmployeeMapper employeeMapper;

    @Override
    public void insert(EmployeeEntity entity) {
        employeeMapper.insert(entity);
    }

    @Override
    public int update(EmployeeEntity entity) {
        return employeeMapper.updateById(entity);
    }

    @Override
    public EmployeeEntity getById(String id) {
        return employeeMapper.selectById(id);
    }

    @Override
    public EmployeeEntity getByUserId(String userId) {
        Wrapper<EmployeeEntity> queryWrapper = new LambdaQueryWrapper<EmployeeEntity>()
                .eq(EmployeeEntity::getUserId, userId);
        return employeeMapper.selectOne(queryWrapper);
    }

    @Override
    public Integer countByDepartmentIds(Set<String> departmentIdSet) {
        return employeeMapper.countByDepartmentIds(departmentIdSet);
    }

    @Override
    public List<EmployeeEntity> selectByIdSet(Set<String> idSet) {
        return employeeMapper.selectByIds(idSet);
    }

    @Override
    public List<EmployeeEntity> selectByUserIdSet(Set<String> userIdSet) {
        Wrapper<EmployeeEntity> queryWrapper = new LambdaQueryWrapper<EmployeeEntity>()
                .in(EmployeeEntity::getUserId, userIdSet);
        return employeeMapper.selectList(queryWrapper);
    }

    @Override
    public List<EmployeeEntity> list4Charge(String departmentId) {
        return employeeMapper.list4Charge(departmentId);
    }

    @Override
    public List<EmployeeEntity> list(HrmEmployeeQueryIListInputDto inputDto) {
        return employeeMapper.list(inputDto);
    }

    @Override
    public List<EmployeeEntity> listChargeByDepartmentIdSet(Set<String> departmentIdSet) {
        return employeeMapper.listChargeByDepartmentIdSet(departmentIdSet);
    }

}
