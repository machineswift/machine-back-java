package com.machine.client.ai.resource.model;

import com.machine.client.ai.resource.model.dto.input.AiResourceProviderCreateInputDto;
import com.machine.client.ai.resource.model.dto.input.AiResourceProviderListInputDto;
import com.machine.client.ai.resource.model.dto.input.AiResourceProviderUpdateInputDto;
import com.machine.client.ai.resource.model.dto.input.AiResourceProviderUpdateStatusInputDto;
import com.machine.client.ai.resource.model.dto.output.AiResourceProviderDetailOutputDto;
import com.machine.client.ai.resource.model.dto.output.AiResourceProviderListOutputDto;
import com.machine.sdk.base.config.OpenFeignMinTimeConfig;
import com.machine.sdk.base.model.request.IdRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(name = "machine-ai-service", path = "machine-ai-service/server/ai/resource_center/provider",
        configuration = OpenFeignMinTimeConfig.class)
public interface IAiResourceProviderClient {

    @PostMapping("create")
    String create(@RequestBody @Validated AiResourceProviderCreateInputDto inputDto);

    @PostMapping("delete")
    int delete(@RequestBody @Validated IdRequest request);

    @PostMapping("update")
    int update(@RequestBody @Validated AiResourceProviderUpdateInputDto inputDto);

    @PostMapping("update_status")
    int updateStatus(@RequestBody @Validated AiResourceProviderUpdateStatusInputDto inputDto);

    @PostMapping("detail")
    AiResourceProviderDetailOutputDto detail(@RequestBody @Validated IdRequest request);

    @PostMapping("list")
    List<AiResourceProviderListOutputDto> list(@RequestBody @Validated AiResourceProviderListInputDto inputDto);
}
