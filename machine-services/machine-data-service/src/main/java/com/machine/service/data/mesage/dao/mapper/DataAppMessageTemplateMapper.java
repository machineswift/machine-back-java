package com.machine.service.data.mesage.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.machine.client.data.message.dto.input.AppMessageTemplateQueryPageInputDto;
import com.machine.service.data.mesage.dao.mapper.entity.DataAppMessageTemplateEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface DataAppMessageTemplateMapper extends BaseMapper<DataAppMessageTemplateEntity> {
    Page<DataAppMessageTemplateEntity> page(@Param("request") AppMessageTemplateQueryPageInputDto request, IPage<DataAppMessageTemplateEntity> page);
}
