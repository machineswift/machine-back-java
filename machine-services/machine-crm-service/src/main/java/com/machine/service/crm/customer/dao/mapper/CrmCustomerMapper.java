package com.machine.service.crm.customer.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.machine.client.crm.customer.dto.input.CrmCustomerQueryPageInputDto;
import com.machine.service.crm.customer.dao.mapper.entity.CrmCustomerEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface CrmCustomerMapper extends BaseMapper<CrmCustomerEntity> {

    Page<CrmCustomerEntity> selectPage(@Param("inputDto") CrmCustomerQueryPageInputDto inputDto,
                                       IPage<CrmCustomerEntity> page);

}
