package com.machine.service.data.tag.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.machine.client.data.tag.dto.input.DataTagQueryPageInputDto;
import com.machine.service.data.tag.dao.mapper.entity.DataTagEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface DataTagMapper extends BaseMapper<DataTagEntity> {

    Page<DataTagEntity> selectPage(@Param("inputDto") DataTagQueryPageInputDto inputDto,
                                    IPage<DataTagEntity> page);
}
