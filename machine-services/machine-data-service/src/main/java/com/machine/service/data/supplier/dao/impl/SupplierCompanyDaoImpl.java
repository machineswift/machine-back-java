package com.machine.service.data.supplier.dao.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.machine.client.data.supplier.dto.input.DataSupplierCompanyPageInputDto;
import com.machine.client.data.supplier.dto.input.DataSupplierCompanyQueryListOffsetInputDto;
import com.machine.client.data.supplier.dto.input.DataSupplierCompanySimplePageInputDto;
import com.machine.sdk.common.envm.StatusEnum;
import com.machine.sdk.common.model.dto.IdDto;
import com.machine.sdk.common.model.dto.IdStatusDto;
import com.machine.sdk.self.envm.EventTypeEnum;
import com.machine.service.data.supplier.dao.ISupplierCompanyDao;
import com.machine.service.data.supplier.dao.mapper.SupplierCompanyMapper;
import com.machine.service.data.supplier.dao.mapper.entity.SupplierCompanyEntity;
import com.machine.service.data.supplier.dao.po.DataSupplierCompanySimpleListPo;
import com.machine.starter.mq.function.CustomerStreamBridge;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public class SupplierCompanyDaoImpl implements ISupplierCompanyDao {

    @Autowired
    private CustomerStreamBridge customerStreamBridge;

    @Autowired
    private SupplierCompanyMapper supplierCompanyMapper;

    @Override
    public String insert(SupplierCompanyEntity entity) {
        supplierCompanyMapper.insert(entity);

        //事件
        customerStreamBridge.sendWebHookEvent(EventTypeEnum.DATA_SUPPLIER_COMPANY_CREATE,
                new IdDto(entity.getId()));
        return entity.getId();
    }

    @Override
    public int updateStatus(String id,
                            StatusEnum status) {
        SupplierCompanyEntity updateEntity = new SupplierCompanyEntity();
        updateEntity.setId(id);
        updateEntity.setStatus(status);

        //事件
        customerStreamBridge.sendWebHookEvent(EventTypeEnum.DATA_SUPPLIER_COMPANY_UPDATE_STATUS,
                new IdStatusDto(id,status));

        return supplierCompanyMapper.updateById(updateEntity);
    }

    @Override
    public int update(SupplierCompanyEntity entity) {
        //事件
        customerStreamBridge.sendWebHookEvent(EventTypeEnum.DATA_SUPPLIER_COMPANY_UPDATE_BASE,
                new IdDto(entity.getId()));

        return supplierCompanyMapper.updateById(entity);
    }

    @Override
    public SupplierCompanyEntity getById(String id) {
        return supplierCompanyMapper.selectById(id);
    }

    @Override
    public SupplierCompanyEntity getByName(String name) {
        Wrapper<SupplierCompanyEntity> queryWrapper = new LambdaQueryWrapper<SupplierCompanyEntity>()
                .eq(SupplierCompanyEntity::getName, name);
        return supplierCompanyMapper.selectOne(queryWrapper);
    }

    @Override
    public SupplierCompanyEntity getByByContactPhone(String contactPhone) {
        Wrapper<SupplierCompanyEntity> queryWrapper = new LambdaQueryWrapper<SupplierCompanyEntity>()
                .eq(SupplierCompanyEntity::getContactPhone, contactPhone);
        return supplierCompanyMapper.selectOne(queryWrapper);
    }

    @Override
    public List<SupplierCompanyEntity> selectByIds(Collection<String> ids) {
        return supplierCompanyMapper.selectByIds(ids);
    }

    @Override
    public List<SupplierCompanyEntity> listBySupplierId(String supplierId) {
        return supplierCompanyMapper.listBySupplierId(supplierId);
    }

    @Override
    public List<SupplierCompanyEntity> listByOffset(DataSupplierCompanyQueryListOffsetInputDto inputDto) {
        return supplierCompanyMapper.listByOffset(inputDto);
    }

    @Override
    public Page<DataSupplierCompanySimpleListPo> pageSample(DataSupplierCompanySimplePageInputDto inputDto) {
        IPage<DataSupplierCompanySimpleListPo> page = new Page<>(inputDto.getCurrent(), inputDto.getSize());
        return supplierCompanyMapper.pageSample(inputDto, page);
    }

    @Override
    public Page<SupplierCompanyEntity> pageExpand(DataSupplierCompanyPageInputDto inputDto) {
        IPage<DataSupplierCompanySimpleListPo> page = new Page<>(inputDto.getCurrent(), inputDto.getSize());
        return supplierCompanyMapper.pageExpand(inputDto, page);
    }
}
