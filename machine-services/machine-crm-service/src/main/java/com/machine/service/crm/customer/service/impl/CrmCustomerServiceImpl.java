package com.machine.service.crm.customer.service.impl;

import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.machine.client.crm.customer.dto.input.CrmCustomerCreateInputDto;
import com.machine.client.crm.customer.dto.input.CrmCustomerQueryPageInputDto;
import com.machine.client.crm.customer.dto.input.CrmCustomerUpdateInputDto;
import com.machine.client.crm.customer.dto.output.CrmCustomerDetailOutputDto;
import com.machine.client.crm.customer.dto.output.CrmCustomerListOutputDto;
import com.machine.client.data.leaf.IDataLeaf4CrmCodeClient;
import com.machine.sdk.common.exception.crm.CrmBusinessException;
import com.machine.sdk.common.model.request.IdRequest;
import com.machine.service.crm.customer.dao.ICrmCustomerDao;
import com.machine.service.crm.customer.service.ICrmCustomerService;
import com.machine.service.crm.customer.dao.mapper.entity.CrmCustomerEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
public class CrmCustomerServiceImpl implements ICrmCustomerService {

    @Autowired
    private ICrmCustomerDao customerDao;

    @Autowired
    private IDataLeaf4CrmCodeClient leaf4CrmCodeClient;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public String create(CrmCustomerCreateInputDto inputDto) {
        //验证身份证号是否存在
        CrmCustomerEntity entityByPhone = customerDao.getByIdentityCardNumber(inputDto.getIdentityCardNumber());
        if (null != entityByPhone) {
            throw new CrmBusinessException("crm.customer.service.create.identityCardNumberAlreadyExists", "客户身份证号已经存在");
        }

        CrmCustomerEntity insertEntity = JSONUtil.toBean(JSONUtil.toJsonStr(inputDto), CrmCustomerEntity.class, true);
        insertEntity.setCode(leaf4CrmCodeClient.customerCode());
        return customerDao.insert(insertEntity);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int delete(IdRequest request) {
        return customerDao.delete(request.getId());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int update(CrmCustomerUpdateInputDto inputDto) {
        CrmCustomerEntity updateEntity = JSONUtil.toBean(JSONUtil.toJsonStr(inputDto), CrmCustomerEntity.class, true);
        return customerDao.update(updateEntity);
    }

    @Override
    public CrmCustomerDetailOutputDto detail(IdRequest request) {
        CrmCustomerEntity entityById = customerDao.getById(request.getId());
        if (null == entityById) {
            return null;
        }
        return JSONUtil.toBean(JSONUtil.toJsonStr(entityById), CrmCustomerDetailOutputDto.class);
    }

    @Override
    public Page<CrmCustomerListOutputDto> selectPage(CrmCustomerQueryPageInputDto inputDto) {
        Page<CrmCustomerEntity> page = customerDao.selectPage(inputDto);
        Page<CrmCustomerListOutputDto> pageResult = new Page<>(page.getCurrent(), page.getSize(), page.getTotal());
        pageResult.setRecords(JSONUtil.toList(JSONUtil.toJsonStr(page.getRecords()), CrmCustomerListOutputDto.class));
        return pageResult;
    }
}
