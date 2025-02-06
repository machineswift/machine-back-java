package com.machine.service.data.messageTemplate.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.machine.client.data.messageTemplate.dto.input.AppMessageTemplateQueryPageInputDto;
import com.machine.service.data.messageTemplate.dao.mapper.entity.AppMessageTemplateEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface AppMessageTemplateMapper extends BaseMapper<AppMessageTemplateEntity> {
    Page<AppMessageTemplateEntity> page(@Param("request") AppMessageTemplateQueryPageInputDto request, IPage<AppMessageTemplateEntity> page);
}
