package com.machine.service.data.employee.dao.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.machine.client.data.employee.dto.input.DataCompanyEmployeeQueryListOffsetInputDto;
import com.machine.sdk.common.envm.data.CompanyEmployeeStatusEnum;
import com.machine.sdk.common.model.dto.IdDto;
import com.machine.sdk.self.domain.data.employee.DataCompanyEmployeeDto;
import com.machine.sdk.self.envm.EventTypeEnum;
import com.machine.service.data.employee.dao.ICompanyEmployeeDao;
import com.machine.service.data.employee.dao.mapper.CompanyEmployeeMapper;
import com.machine.service.data.employee.dao.mapper.entity.CompanyEmployeeEntity;
import com.machine.starter.mq.function.CustomerStreamBridge;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public class CompanyEmployeeDaoImpl implements ICompanyEmployeeDao {

    @Autowired
    private CustomerStreamBridge customerStreamBridge;

    @Autowired
    private CompanyEmployeeMapper companyEmployeeMapper;

    @Override
    public String insert(CompanyEmployeeEntity entity) {
        companyEmployeeMapper.insert(entity);

        //事件
        customerStreamBridge.sendWebHookEvent(EventTypeEnum.DATA_COMPANY_EMPLOYEE_CREATE, new IdDto(entity.getId()));

        return entity.getId();
    }

    @Override
    public int update(CompanyEmployeeEntity entity) {
        //事件
        customerStreamBridge.sendWebHookEvent(EventTypeEnum.DATA_COMPANY_EMPLOYEE_UPDATE_BASE, new IdDto(entity.getId()));

        return companyEmployeeMapper.updateById(entity);
    }

    @Override
    public int updateStatus(String id,
                            CompanyEmployeeStatusEnum employeeStatus) {
        CompanyEmployeeEntity entity = new CompanyEmployeeEntity();
        entity.setId(id);
        entity.setEmployeeStatus(employeeStatus);

        //事件
        customerStreamBridge.sendWebHookEvent(EventTypeEnum.DATA_COMPANY_EMPLOYEE_UPDATE_STATUS,
                new DataCompanyEmployeeDto(entity.getId(),employeeStatus));

        return companyEmployeeMapper.updateById(entity);
    }

    @Override
    public CompanyEmployeeEntity getById(String id) {
        return companyEmployeeMapper.selectById(id);
    }

    @Override
    public CompanyEmployeeEntity getByUserId(String userId) {
        Wrapper<CompanyEmployeeEntity> deleteWrapper = new LambdaQueryWrapper<CompanyEmployeeEntity>()
                .eq(CompanyEmployeeEntity::getUserId, userId);
        return companyEmployeeMapper.selectOne(deleteWrapper);
    }

    @Override
    public List<CompanyEmployeeEntity> selectByUserIdSet(Set<String> userIdSet) {
        Wrapper<CompanyEmployeeEntity> deleteWrapper = new LambdaQueryWrapper<CompanyEmployeeEntity>()
                .in(CompanyEmployeeEntity::getUserId, userIdSet);
        return companyEmployeeMapper.selectList(deleteWrapper);
    }

    @Override
    public List<CompanyEmployeeEntity> listByOffset(DataCompanyEmployeeQueryListOffsetInputDto inputDto) {
        return companyEmployeeMapper.listByOffset(inputDto);
    }
}
