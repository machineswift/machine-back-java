package com.machine.service.data.supplier.dao;

import com.machine.service.data.supplier.dao.mapper.entity.SupplierCompanyRelationEntity;

import java.util.List;

public interface ISupplierCompanyRelationDao {
    void insert(SupplierCompanyRelationEntity entity);

    int deleteBySupplierId(String supplierId);

    List<SupplierCompanyRelationEntity> selectBySupplierId(String supplierId);
}
