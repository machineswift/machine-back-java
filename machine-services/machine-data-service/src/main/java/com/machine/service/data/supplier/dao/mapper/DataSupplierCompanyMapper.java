package com.machine.service.data.supplier.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.machine.client.data.supplier.dto.input.DataSupplierCompanyPageInputDto;
import com.machine.client.data.supplier.dto.input.DataSupplierCompanyQueryListOffsetInputDto;
import com.machine.client.data.supplier.dto.input.DataSupplierCompanySimplePageInputDto;
import com.machine.service.data.supplier.dao.mapper.entity.DataSupplierCompanyEntity;
import com.machine.service.data.supplier.dao.po.DataSupplierCompanySimpleListPo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface DataSupplierCompanyMapper extends BaseMapper<DataSupplierCompanyEntity> {

    List<DataSupplierCompanyEntity> listBySupplierId(@Param("supplierId") String supplierId);

    List<DataSupplierCompanyEntity> listByOffset(@Param("inputDto") DataSupplierCompanyQueryListOffsetInputDto inputDto);

    Page<DataSupplierCompanySimpleListPo> pageSample(@Param("inputDto") DataSupplierCompanySimplePageInputDto inputDto,
                                                     IPage<DataSupplierCompanySimpleListPo> page);

    Page<DataSupplierCompanyEntity> pageExpand(@Param("inputDto") DataSupplierCompanyPageInputDto inputDto,
                                               IPage<DataSupplierCompanySimpleListPo> page);

}
