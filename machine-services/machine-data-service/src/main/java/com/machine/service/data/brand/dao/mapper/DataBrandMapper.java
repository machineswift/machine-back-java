package com.machine.service.data.brand.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.machine.client.data.brand.dto.input.DataBrandQueryPageInputDto;
import com.machine.client.data.brand.dto.input.DataBrandQuerySimplePageInputDto;
import com.machine.service.data.brand.dao.mapper.entity.DataBrandEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Set;

@Mapper
public interface DataBrandMapper extends BaseMapper<DataBrandEntity> {

    Set<String> selectHasChildrenIdSet(@Param("idSet") Set<String> idSet);

    List<DataBrandEntity> selectAncestorList(@Param("id") String id);

    Page<DataBrandEntity> selectPage(@Param("inputDto") DataBrandQueryPageInputDto inputDto,
                                     IPage<DataBrandEntity> page);

    Page<DataBrandEntity> selectChildrenPage(@Param("inputDto") DataBrandQueryPageInputDto inputDto,
                                             IPage<DataBrandEntity> page);

    Page<DataBrandEntity> selectSimplePage(@Param("inputDto") DataBrandQuerySimplePageInputDto inputDto,
                                          IPage<DataBrandEntity> page);

}
