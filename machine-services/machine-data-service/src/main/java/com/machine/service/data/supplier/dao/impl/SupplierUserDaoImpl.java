package com.machine.service.data.supplier.dao.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.machine.client.data.supplier.dto.input.DataSupplierQueryListOffsetInputDto;
import com.machine.sdk.common.model.dto.IdDto;
import com.machine.sdk.self.envm.EventTypeEnum;
import com.machine.service.data.supplier.dao.ISupplierUserDao;
import com.machine.service.data.supplier.dao.mapper.SupplierUserMapper;
import com.machine.service.data.supplier.dao.mapper.entity.SupplierUserEntity;
import com.machine.starter.mq.function.CustomerStreamBridge;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class SupplierUserDaoImpl implements ISupplierUserDao {

    @Autowired
    private CustomerStreamBridge customerStreamBridge;

    @Autowired
    private SupplierUserMapper supplierUserMapper;

    @Override
    public String insert(SupplierUserEntity entity) {
        supplierUserMapper.insert(entity);

        //事件
        customerStreamBridge.sendWebHookEvent(EventTypeEnum.DATA_SUPPLIER_CREATE, new IdDto(entity.getId()));

        return entity.getId();
    }

    @Override
    public SupplierUserEntity getById(String id) {
        return supplierUserMapper.selectById(id);
    }

    @Override
    public SupplierUserEntity getByUserId(String userId) {
        Wrapper<SupplierUserEntity> queryWrapper = new LambdaQueryWrapper<SupplierUserEntity>()
                .eq(SupplierUserEntity::getUserId, userId);
        return supplierUserMapper.selectOne(queryWrapper);
    }

    @Override
    public List<SupplierUserEntity> listByOffset(DataSupplierQueryListOffsetInputDto inputDto) {
        return supplierUserMapper.listByOffset(inputDto);
    }
}
