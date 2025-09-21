package com.machine.service.crm.customer.dao.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.machine.client.crm.customer.dto.input.CrmCustomerQueryPageInputDto;
import com.machine.service.crm.customer.dao.ICrmCustomerDao;
import com.machine.service.crm.customer.dao.mapper.CrmCustomerMapper;
import com.machine.service.crm.customer.dao.mapper.entity.CrmCustomerEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class CrmCustomerDaoImpl implements ICrmCustomerDao {

    @Autowired
    private CrmCustomerMapper customerMapper;

    @Override
    public String insert(CrmCustomerEntity entity) {
        customerMapper.insert(entity);
        return entity.getId();
    }

    @Override
    public int delete(String id) {
        return customerMapper.deleteById(id);
    }

    @Override
    public int update(CrmCustomerEntity entity) {
        return customerMapper.updateById(entity);
    }

    @Override
    public CrmCustomerEntity getById(String id) {
        return customerMapper.selectById(id);
    }

    @Override
    public CrmCustomerEntity getByIdentityCardNumber(String identityCardNumber) {
        Wrapper<CrmCustomerEntity> queryWrapper = new LambdaQueryWrapper<CrmCustomerEntity>()
                .eq(CrmCustomerEntity::getIdentityCardNumber, identityCardNumber);
        return customerMapper.selectOne(queryWrapper);
    }

    @Override
    public Page<CrmCustomerEntity> selectPage(CrmCustomerQueryPageInputDto inputDto) {
        IPage<CrmCustomerEntity> page = new Page<>(inputDto.getCurrent(), inputDto.getSize());
        return customerMapper.selectPage(inputDto, page);
    }
}
