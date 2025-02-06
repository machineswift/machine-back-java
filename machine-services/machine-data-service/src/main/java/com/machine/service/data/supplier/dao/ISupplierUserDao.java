package com.machine.service.data.supplier.dao;

import com.machine.client.data.supplier.dto.input.DataSupplierQueryListOffsetInputDto;
import com.machine.service.data.supplier.dao.mapper.entity.SupplierUserEntity;

import java.util.List;

public interface ISupplierUserDao {
    
    String insert(SupplierUserEntity entity);

    SupplierUserEntity getById(String id);
    
    SupplierUserEntity getByUserId(String userId);

    List<SupplierUserEntity> listByOffset(DataSupplierQueryListOffsetInputDto inputDto);
}
