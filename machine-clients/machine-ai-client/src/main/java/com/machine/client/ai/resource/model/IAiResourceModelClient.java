package com.machine.client.ai.resource.model;

import com.machine.client.ai.resource.model.dto.input.AiResourceModelCreateInputDto;
import com.machine.client.ai.resource.model.dto.input.AiResourceModelQueryPageInputDto;
import com.machine.client.ai.resource.model.dto.input.AiResourceModelUpdateInputDto;
import com.machine.client.ai.resource.model.dto.input.AiResourceModelUpdateStatusInputDto;
import com.machine.client.ai.resource.model.dto.output.AiResourceModelDetailOutputDto;
import com.machine.client.ai.resource.model.dto.output.AiResourceModelListOutputDto;
import com.machine.sdk.base.config.OpenFeignMinTimeConfig;
import com.machine.sdk.base.model.request.IdRequest;
import com.machine.sdk.base.model.response.PageResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "machine-ai-service", path = "machine-ai-service/server/ai/resource_center/model",
        configuration = OpenFeignMinTimeConfig.class)
public interface IAiResourceModelClient {

    @PostMapping("create")
    String create(@RequestBody @Validated AiResourceModelCreateInputDto inputDto);

    @PostMapping("delete")
    int delete(@RequestBody @Validated IdRequest request);

    @PostMapping("update")
    int update(@RequestBody @Validated AiResourceModelUpdateInputDto inputDto);

    @PostMapping("update_status")
    int updateStatus(@RequestBody @Validated AiResourceModelUpdateStatusInputDto inputDto);

    @PostMapping("detail")
    AiResourceModelDetailOutputDto detail(@RequestBody @Validated IdRequest request);

    @PostMapping("select_page")
    PageResponse<AiResourceModelListOutputDto> selectPage(@RequestBody @Validated AiResourceModelQueryPageInputDto inputDto);
}
