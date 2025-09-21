package com.machine.service.data.supplier.dao;

import com.machine.service.data.supplier.dao.mapper.entity.DataSupplierCompanyRelationEntity;

import java.util.List;

public interface IDataSupplierCompanyRelationDao {
    void insert(DataSupplierCompanyRelationEntity entity);

    int deleteBySupplierId(String supplierId);

    List<DataSupplierCompanyRelationEntity> selectBySupplierId(String supplierId);
}
