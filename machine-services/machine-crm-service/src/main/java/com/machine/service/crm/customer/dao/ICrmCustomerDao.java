package com.machine.service.crm.customer.dao;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.machine.client.crm.customer.dto.input.CrmCustomerQueryPageInputDto;
import com.machine.service.crm.customer.dao.mapper.entity.CrmCustomerEntity;

public interface ICrmCustomerDao {

    String insert(CrmCustomerEntity entity);

    int delete(String id);

    int update(CrmCustomerEntity entity);

    CrmCustomerEntity getById(String id);

    CrmCustomerEntity getByIdentityCardNumber(String identityCardNumber);

    Page<CrmCustomerEntity> selectPage(CrmCustomerQueryPageInputDto inputDto);

}
