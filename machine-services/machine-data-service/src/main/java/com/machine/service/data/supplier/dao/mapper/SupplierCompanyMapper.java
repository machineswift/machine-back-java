package com.machine.service.data.supplier.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.machine.client.data.supplier.dto.input.DataSupplierCompanyPageInputDto;
import com.machine.client.data.supplier.dto.input.DataSupplierCompanyQueryListOffsetInputDto;
import com.machine.client.data.supplier.dto.input.DataSupplierCompanySimplePageInputDto;
import com.machine.service.data.supplier.dao.mapper.entity.SupplierCompanyEntity;
import com.machine.service.data.supplier.dao.po.DataSupplierCompanySimpleListPo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface SupplierCompanyMapper extends BaseMapper<SupplierCompanyEntity> {

    List<SupplierCompanyEntity> listBySupplierId(@Param("supplierId") String supplierId);

    List<SupplierCompanyEntity> listByOffset(@Param("inputDto") DataSupplierCompanyQueryListOffsetInputDto inputDto);

    Page<DataSupplierCompanySimpleListPo> pageSample(@Param("inputDto") DataSupplierCompanySimplePageInputDto inputDto,
                                                     IPage<DataSupplierCompanySimpleListPo> page);

    Page<SupplierCompanyEntity> pageExpand(@Param("inputDto") DataSupplierCompanyPageInputDto inputDto,
                                           IPage<DataSupplierCompanySimpleListPo> page);

}
