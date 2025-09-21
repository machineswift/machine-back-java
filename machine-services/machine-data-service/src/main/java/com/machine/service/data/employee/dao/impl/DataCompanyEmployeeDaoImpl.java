package com.machine.service.data.employee.dao.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.machine.client.data.employee.dto.input.DataCompanyEmployeeListUserIdInputDto;
import com.machine.client.data.employee.dto.input.DataCompanyEmployeeQueryListOffsetInputDto;
import com.machine.sdk.common.envm.hrm.HrmEmployeeStatusEnum;
import com.machine.sdk.common.model.dto.IdDto;
import com.machine.sdk.self.domain.data.employee.DataCompanyEmployeeDto;
import com.machine.sdk.self.envm.EventTypeEnum;
import com.machine.service.data.employee.dao.IDataCompanyEmployeeDao;
import com.machine.service.data.employee.dao.mapper.DataCompanyEmployeeMapper;
import com.machine.service.data.employee.dao.mapper.entity.DataCompanyEmployeeEntity;
import com.machine.starter.mq.function.CustomerStreamBridge;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public class DataCompanyEmployeeDaoImpl implements IDataCompanyEmployeeDao {

    @Autowired
    private CustomerStreamBridge customerStreamBridge;

    @Autowired
    private DataCompanyEmployeeMapper dataCompanyEmployeeMapper;

    @Override
    public String insert(DataCompanyEmployeeEntity entity) {
        dataCompanyEmployeeMapper.insert(entity);

        //事件
        customerStreamBridge.sendWebHookEvent(EventTypeEnum.DATA_COMPANY_EMPLOYEE_CREATE, new IdDto(entity.getId()));

        return entity.getId();
    }

    @Override
    public int update(DataCompanyEmployeeEntity entity) {
        //事件
        customerStreamBridge.sendWebHookEvent(EventTypeEnum.DATA_COMPANY_EMPLOYEE_UPDATE_BASE, new IdDto(entity.getId()));

        return dataCompanyEmployeeMapper.updateById(entity);
    }

    @Override
    public int updateStatus(String id,
                            HrmEmployeeStatusEnum employeeStatus) {
        DataCompanyEmployeeEntity entity = new DataCompanyEmployeeEntity();
        entity.setId(id);
        entity.setEmployeeStatus(employeeStatus);

        //事件
        customerStreamBridge.sendWebHookEvent(EventTypeEnum.DATA_COMPANY_EMPLOYEE_UPDATE_STATUS,
                new DataCompanyEmployeeDto(entity.getId(),employeeStatus));

        return dataCompanyEmployeeMapper.updateById(entity);
    }

    @Override
    public DataCompanyEmployeeEntity getById(String id) {
        return dataCompanyEmployeeMapper.selectById(id);
    }

    @Override
    public DataCompanyEmployeeEntity getByUserId(String userId) {
        Wrapper<DataCompanyEmployeeEntity> deleteWrapper = new LambdaQueryWrapper<DataCompanyEmployeeEntity>()
                .eq(DataCompanyEmployeeEntity::getUserId, userId);
        return dataCompanyEmployeeMapper.selectOne(deleteWrapper);
    }

    @Override
    public Set<String> listUserIdByCondition(DataCompanyEmployeeListUserIdInputDto inputDto) {
        return dataCompanyEmployeeMapper.listUserIdByCondition(inputDto);
    }

    @Override
    public List<DataCompanyEmployeeEntity> selectByUserIdSet(Set<String> userIdSet) {
        Wrapper<DataCompanyEmployeeEntity> deleteWrapper = new LambdaQueryWrapper<DataCompanyEmployeeEntity>()
                .in(DataCompanyEmployeeEntity::getUserId, userIdSet);
        return dataCompanyEmployeeMapper.selectList(deleteWrapper);
    }

    @Override
    public List<DataCompanyEmployeeEntity> listByOffset(DataCompanyEmployeeQueryListOffsetInputDto inputDto) {
        return dataCompanyEmployeeMapper.listByOffset(inputDto);
    }
}
