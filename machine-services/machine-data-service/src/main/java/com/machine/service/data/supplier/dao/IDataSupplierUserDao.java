package com.machine.service.data.supplier.dao;

import com.machine.client.data.supplier.dto.input.DataSupplierListUserIdInputDto;
import com.machine.client.data.supplier.dto.input.DataSupplierQueryListOffsetInputDto;
import com.machine.service.data.supplier.dao.mapper.entity.DataSupplierUserEntity;

import java.util.List;
import java.util.Set;

public interface IDataSupplierUserDao {
    
    String insert(DataSupplierUserEntity entity);

    DataSupplierUserEntity getById(String id);
    
    DataSupplierUserEntity getByUserId(String userId);

    Set<String> listUserIdByCondition(DataSupplierListUserIdInputDto inputDto);

    List<DataSupplierUserEntity> listByOffset(DataSupplierQueryListOffsetInputDto inputDto);

}
