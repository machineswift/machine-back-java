package com.machine.service.data.file.material.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.machine.client.data.file.material.dto.input.DataMaterialQueryPageInputDto;
import com.machine.service.data.file.material.dao.mapper.entity.DataMaterialEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface DataMaterialMapper extends BaseMapper<DataMaterialEntity> {

    Page<DataMaterialEntity> selectPage(@Param("inputDto") DataMaterialQueryPageInputDto inputDto,
                                        IPage<DataMaterialEntity> page);

}
