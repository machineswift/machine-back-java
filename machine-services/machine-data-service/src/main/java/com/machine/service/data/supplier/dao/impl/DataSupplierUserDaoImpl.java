package com.machine.service.data.supplier.dao.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.machine.client.data.supplier.dto.input.DataSupplierListUserIdInputDto;
import com.machine.client.data.supplier.dto.input.DataSupplierQueryListOffsetInputDto;
import com.machine.sdk.common.model.dto.IdDto;
import com.machine.sdk.self.envm.EventTypeEnum;
import com.machine.service.data.supplier.dao.IDataSupplierUserDao;
import com.machine.service.data.supplier.dao.mapper.DataSupplierUserMapper;
import com.machine.service.data.supplier.dao.mapper.entity.DataSupplierUserEntity;
import com.machine.starter.mq.function.CustomerStreamBridge;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public class DataSupplierUserDaoImpl implements IDataSupplierUserDao {

    @Autowired
    private CustomerStreamBridge customerStreamBridge;

    @Autowired
    private DataSupplierUserMapper dataSupplierUserMapper;

    @Override
    public String insert(DataSupplierUserEntity entity) {
        dataSupplierUserMapper.insert(entity);

        //事件
        customerStreamBridge.sendWebHookEvent(EventTypeEnum.DATA_SUPPLIER_CREATE, new IdDto(entity.getId()));

        return entity.getId();
    }

    @Override
    public DataSupplierUserEntity getById(String id) {
        return dataSupplierUserMapper.selectById(id);
    }

    @Override
    public DataSupplierUserEntity getByUserId(String userId) {
        Wrapper<DataSupplierUserEntity> queryWrapper = new LambdaQueryWrapper<DataSupplierUserEntity>()
                .eq(DataSupplierUserEntity::getUserId, userId);
        return dataSupplierUserMapper.selectOne(queryWrapper);
    }

    @Override
    public Set<String> listUserIdByCondition(DataSupplierListUserIdInputDto inputDto) {
        return dataSupplierUserMapper.listUserIdByCondition(inputDto);
    }

    @Override
    public List<DataSupplierUserEntity> listByOffset(DataSupplierQueryListOffsetInputDto inputDto) {
        return dataSupplierUserMapper.listByOffset(inputDto);
    }
}
