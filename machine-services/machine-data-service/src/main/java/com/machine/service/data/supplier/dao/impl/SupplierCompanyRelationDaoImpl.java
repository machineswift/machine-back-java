package com.machine.service.data.supplier.dao.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.machine.sdk.common.model.dto.IdDto;
import com.machine.sdk.self.envm.EventTypeEnum;
import com.machine.service.data.supplier.dao.ISupplierCompanyRelationDao;
import com.machine.service.data.supplier.dao.mapper.SupplierCompanyRelationMapper;
import com.machine.service.data.supplier.dao.mapper.entity.SupplierCompanyRelationEntity;
import com.machine.starter.mq.function.CustomerStreamBridge;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class SupplierCompanyRelationDaoImpl implements ISupplierCompanyRelationDao {

    @Autowired
    private CustomerStreamBridge customerStreamBridge;

    @Autowired
    private SupplierCompanyRelationMapper supplierCompanyRelationMapper;

    @Override
    public void insert(SupplierCompanyRelationEntity entity) {
        supplierCompanyRelationMapper.insert(entity);
    }

    @Override
    public int deleteBySupplierId(String supplierId) {
        Wrapper<SupplierCompanyRelationEntity> deleteWrapper = new LambdaQueryWrapper<SupplierCompanyRelationEntity>()
                .eq(SupplierCompanyRelationEntity::getSupplierId, supplierId);

        //事件
        customerStreamBridge.sendWebHookEvent(EventTypeEnum.DATA_SUPPLIER_UPDATE_SUPPLIER_COMPANY, new IdDto(supplierId));

        return supplierCompanyRelationMapper.delete(deleteWrapper);
    }

    @Override
    public List<SupplierCompanyRelationEntity> selectBySupplierId(String supplierId) {
        Wrapper<SupplierCompanyRelationEntity> selectWrapper = new LambdaQueryWrapper<SupplierCompanyRelationEntity>()
                .eq(SupplierCompanyRelationEntity::getSupplierId, supplierId);
        return supplierCompanyRelationMapper.selectList(selectWrapper);
    }
}
