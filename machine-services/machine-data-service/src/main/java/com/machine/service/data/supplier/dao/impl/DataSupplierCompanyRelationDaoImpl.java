package com.machine.service.data.supplier.dao.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.machine.sdk.common.model.dto.IdDto;
import com.machine.sdk.self.envm.EventTypeEnum;
import com.machine.service.data.supplier.dao.IDataSupplierCompanyRelationDao;
import com.machine.service.data.supplier.dao.mapper.DataSupplierCompanyRelationMapper;
import com.machine.service.data.supplier.dao.mapper.entity.DataSupplierCompanyRelationEntity;
import com.machine.starter.mq.function.CustomerStreamBridge;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class DataSupplierCompanyRelationDaoImpl implements IDataSupplierCompanyRelationDao {

    @Autowired
    private CustomerStreamBridge customerStreamBridge;

    @Autowired
    private DataSupplierCompanyRelationMapper dataSupplierCompanyRelationMapper;

    @Override
    public void insert(DataSupplierCompanyRelationEntity entity) {
        dataSupplierCompanyRelationMapper.insert(entity);
    }

    @Override
    public int deleteBySupplierId(String supplierId) {
        Wrapper<DataSupplierCompanyRelationEntity> deleteWrapper = new LambdaQueryWrapper<DataSupplierCompanyRelationEntity>()
                .eq(DataSupplierCompanyRelationEntity::getSupplierId, supplierId);

        //事件
        customerStreamBridge.sendWebHookEvent(EventTypeEnum.DATA_SUPPLIER_UPDATE_SUPPLIER_COMPANY, new IdDto(supplierId));

        return dataSupplierCompanyRelationMapper.delete(deleteWrapper);
    }

    @Override
    public List<DataSupplierCompanyRelationEntity> selectBySupplierId(String supplierId) {
        Wrapper<DataSupplierCompanyRelationEntity> selectWrapper = new LambdaQueryWrapper<DataSupplierCompanyRelationEntity>()
                .eq(DataSupplierCompanyRelationEntity::getSupplierId, supplierId);
        return dataSupplierCompanyRelationMapper.selectList(selectWrapper);
    }
}
