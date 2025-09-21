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
import com.machine.service.data.supplier.dao.IDataSupplierCompanyDao;
import com.machine.service.data.supplier.dao.mapper.DataSupplierCompanyMapper;
import com.machine.service.data.supplier.dao.mapper.entity.DataSupplierCompanyEntity;
import com.machine.service.data.supplier.dao.po.DataSupplierCompanySimpleListPo;
import com.machine.starter.mq.function.CustomerStreamBridge;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public class DataSupplierCompanyDaoImpl implements IDataSupplierCompanyDao {

    @Autowired
    private CustomerStreamBridge customerStreamBridge;

    @Autowired
    private DataSupplierCompanyMapper dataSupplierCompanyMapper;

    @Override
    public String insert(DataSupplierCompanyEntity entity) {
        dataSupplierCompanyMapper.insert(entity);

        //事件
        customerStreamBridge.sendWebHookEvent(EventTypeEnum.DATA_SUPPLIER_COMPANY_CREATE,
                new IdDto(entity.getId()));
        return entity.getId();
    }

    @Override
    public int updateStatus(String id,
                            StatusEnum status) {
        DataSupplierCompanyEntity updateEntity = new DataSupplierCompanyEntity();
        updateEntity.setId(id);
        updateEntity.setStatus(status);

        //事件
        customerStreamBridge.sendWebHookEvent(EventTypeEnum.DATA_SUPPLIER_COMPANY_UPDATE_STATUS,
                new IdStatusDto(id,status));

        return dataSupplierCompanyMapper.updateById(updateEntity);
    }

    @Override
    public int update(DataSupplierCompanyEntity entity) {
        //事件
        customerStreamBridge.sendWebHookEvent(EventTypeEnum.DATA_SUPPLIER_COMPANY_UPDATE_BASE,
                new IdDto(entity.getId()));

        return dataSupplierCompanyMapper.updateById(entity);
    }

    @Override
    public DataSupplierCompanyEntity getById(String id) {
        return dataSupplierCompanyMapper.selectById(id);
    }

    @Override
    public DataSupplierCompanyEntity getByName(String name) {
        Wrapper<DataSupplierCompanyEntity> queryWrapper = new LambdaQueryWrapper<DataSupplierCompanyEntity>()
                .eq(DataSupplierCompanyEntity::getName, name);
        return dataSupplierCompanyMapper.selectOne(queryWrapper);
    }

    @Override
    public DataSupplierCompanyEntity getByByContactPhone(String contactPhone) {
        Wrapper<DataSupplierCompanyEntity> queryWrapper = new LambdaQueryWrapper<DataSupplierCompanyEntity>()
                .eq(DataSupplierCompanyEntity::getContactPhone, contactPhone);
        return dataSupplierCompanyMapper.selectOne(queryWrapper);
    }

    @Override
    public List<DataSupplierCompanyEntity> selectByIds(Collection<String> ids) {
        return dataSupplierCompanyMapper.selectByIds(ids);
    }

    @Override
    public List<DataSupplierCompanyEntity> listBySupplierId(String supplierId) {
        return dataSupplierCompanyMapper.listBySupplierId(supplierId);
    }

    @Override
    public List<DataSupplierCompanyEntity> listByOffset(DataSupplierCompanyQueryListOffsetInputDto inputDto) {
        return dataSupplierCompanyMapper.listByOffset(inputDto);
    }

    @Override
    public Page<DataSupplierCompanySimpleListPo> pageSample(DataSupplierCompanySimplePageInputDto inputDto) {
        IPage<DataSupplierCompanySimpleListPo> page = new Page<>(inputDto.getCurrent(), inputDto.getSize());
        return dataSupplierCompanyMapper.pageSample(inputDto, page);
    }

    @Override
    public Page<DataSupplierCompanyEntity> pageExpand(DataSupplierCompanyPageInputDto inputDto) {
        IPage<DataSupplierCompanySimpleListPo> page = new Page<>(inputDto.getCurrent(), inputDto.getSize());
        return dataSupplierCompanyMapper.pageExpand(inputDto, page);
    }
}
