package com.machine.service.ai.resource.model.service;

import com.machine.client.ai.resource.model.dto.input.AiResourceProviderCreateInputDto;
import com.machine.client.ai.resource.model.dto.input.AiResourceProviderUpdateInputDto;
import com.machine.client.ai.resource.model.dto.input.AiResourceProviderUpdateStatusInputDto;
import com.machine.client.ai.resource.model.dto.output.AiResourceProviderDetailOutputDto;
import com.machine.client.ai.resource.model.dto.output.AiResourceProviderListOutputDto;
import com.machine.sdk.base.envm.StatusEnum;

import java.util.List;

public interface IAiResourceProviderService {

    String create(AiResourceProviderCreateInputDto inputDto);

    int deleteById(String id);

    int update(AiResourceProviderUpdateInputDto inputDto);

    int updateStatus(AiResourceProviderUpdateStatusInputDto inputDto);

    AiResourceProviderDetailOutputDto detail(String id);

    List<AiResourceProviderListOutputDto> list(StatusEnum status);
}
