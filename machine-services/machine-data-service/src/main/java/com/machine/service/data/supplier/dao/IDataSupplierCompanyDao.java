package com.machine.service.data.supplier.dao;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.machine.client.data.supplier.dto.input.DataSupplierCompanyPageInputDto;
import com.machine.client.data.supplier.dto.input.DataSupplierCompanyQueryListOffsetInputDto;
import com.machine.client.data.supplier.dto.input.DataSupplierCompanySimplePageInputDto;
import com.machine.sdk.common.envm.StatusEnum;
import com.machine.service.data.supplier.dao.mapper.entity.DataSupplierCompanyEntity;
import com.machine.service.data.supplier.dao.po.DataSupplierCompanySimpleListPo;

import java.util.Collection;
import java.util.List;

public interface IDataSupplierCompanyDao {

    String insert(DataSupplierCompanyEntity entity);

    int updateStatus(String id,
                     StatusEnum status);

    int update(DataSupplierCompanyEntity entity);

    DataSupplierCompanyEntity getById(String id);

    DataSupplierCompanyEntity getByName(String name);

    DataSupplierCompanyEntity getByByContactPhone(String contactPhone);

    List<DataSupplierCompanyEntity> selectByIds(Collection<String> ids);

    List<DataSupplierCompanyEntity> listBySupplierId(String supplierId);

    List<DataSupplierCompanyEntity> listByOffset(DataSupplierCompanyQueryListOffsetInputDto inputDto);

    Page<DataSupplierCompanySimpleListPo> pageSample(DataSupplierCompanySimplePageInputDto inputDto);

    Page<DataSupplierCompanyEntity> pageExpand(DataSupplierCompanyPageInputDto inputDto);
}
