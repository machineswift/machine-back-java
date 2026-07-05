package com.machine.service.ai.resource.model.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.machine.client.ai.resource.model.dto.input.AiResourceModelCreateInputDto;
import com.machine.client.ai.resource.model.dto.input.AiResourceModelQueryPageInputDto;
import com.machine.client.ai.resource.model.dto.input.AiResourceModelUpdateInputDto;
import com.machine.client.ai.resource.model.dto.input.AiResourceModelUpdateStatusInputDto;
import com.machine.client.ai.resource.model.dto.output.AiResourceModelDetailOutputDto;
import com.machine.client.ai.resource.model.dto.output.AiResourceModelListOutputDto;

public interface IAiResourceModelService {

    String create(AiResourceModelCreateInputDto inputDto);

    int deleteById(String id);

    int update(AiResourceModelUpdateInputDto inputDto);

    int updateStatus(AiResourceModelUpdateStatusInputDto inputDto);

    AiResourceModelDetailOutputDto detail(String id);

    Page<AiResourceModelListOutputDto> selectPage(AiResourceModelQueryPageInputDto inputDto);
}
