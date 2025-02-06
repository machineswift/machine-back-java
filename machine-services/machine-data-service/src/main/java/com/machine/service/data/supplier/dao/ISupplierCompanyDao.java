package com.machine.service.data.supplier.dao;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.machine.client.data.supplier.dto.input.DataSupplierCompanyPageInputDto;
import com.machine.client.data.supplier.dto.input.DataSupplierCompanyQueryListOffsetInputDto;
import com.machine.client.data.supplier.dto.input.DataSupplierCompanySimplePageInputDto;
import com.machine.sdk.common.envm.StatusEnum;
import com.machine.service.data.supplier.dao.mapper.entity.SupplierCompanyEntity;
import com.machine.service.data.supplier.dao.po.DataSupplierCompanySimpleListPo;

import java.util.Collection;
import java.util.List;

public interface ISupplierCompanyDao {

    String insert(SupplierCompanyEntity entity);

    int updateStatus(String id,
                     StatusEnum status);

    int update(SupplierCompanyEntity entity);

    SupplierCompanyEntity getById(String id);

    SupplierCompanyEntity getByName(String name);

    SupplierCompanyEntity getByByContactPhone(String contactPhone);

    List<SupplierCompanyEntity> selectByIds(Collection<String> ids);

    List<SupplierCompanyEntity> listBySupplierId(String supplierId);

    List<SupplierCompanyEntity> listByOffset(DataSupplierCompanyQueryListOffsetInputDto inputDto);

    Page<DataSupplierCompanySimpleListPo> pageSample(DataSupplierCompanySimplePageInputDto inputDto);

    Page<SupplierCompanyEntity> pageExpand(DataSupplierCompanyPageInputDto inputDto);
}
