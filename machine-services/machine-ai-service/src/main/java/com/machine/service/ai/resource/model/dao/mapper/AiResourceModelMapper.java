package com.machine.service.ai.resource.model.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.machine.client.ai.resource.model.dto.input.AiResourceModelQueryPageInputDto;
import com.machine.service.ai.resource.model.dao.mapper.entity.AiResourceModelEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface AiResourceModelMapper extends BaseMapper<AiResourceModelEntity> {

    Page<AiResourceModelEntity> selectPage(@Param("inputDto") AiResourceModelQueryPageInputDto inputDto,
                                           IPage<AiResourceModelEntity> page);
}
