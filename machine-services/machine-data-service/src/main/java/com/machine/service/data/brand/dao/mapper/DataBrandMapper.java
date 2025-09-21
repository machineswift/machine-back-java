package com.machine.service.data.brand.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.machine.client.data.brand.dto.input.DataBrandQueryPageInputDto;
import com.machine.service.data.brand.dao.mapper.entity.DataBrandEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface DataBrandMapper extends BaseMapper<DataBrandEntity> {

    Page<DataBrandEntity> selectPage(@Param("inputDto") DataBrandQueryPageInputDto inputDto,
                                     IPage<DataBrandEntity> page);

}
