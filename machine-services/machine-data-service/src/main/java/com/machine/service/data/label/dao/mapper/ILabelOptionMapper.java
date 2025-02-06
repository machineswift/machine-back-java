package com.machine.service.data.label.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.machine.client.data.label.dto.input.DataLabelOptionQueryListOffsetInputDto;
import com.machine.client.data.label.dto.input.DataLabelOptionQueryPageInputDto;
import com.machine.service.data.label.dao.mapper.entity.LabelOptionEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ILabelOptionMapper extends BaseMapper<LabelOptionEntity> {

    List<LabelOptionEntity> listByOffset(@Param("inputDto") DataLabelOptionQueryListOffsetInputDto inputDto);

    Page<LabelOptionEntity> selectPage(@Param("inputDto") DataLabelOptionQueryPageInputDto inputDto,
                                       IPage<LabelOptionEntity> page);

}
