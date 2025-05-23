package com.machine.client.data.messageTemplate;

import com.machine.client.data.messageTemplate.dto.input.*;
import com.machine.client.data.messageTemplate.dto.output.AppMessageTemplateDetailOutputDto;
import com.machine.client.data.messageTemplate.dto.output.AppMessageTemplateListOutPutDto;
import com.machine.sdk.common.config.OpenFeignDefaultConfig;
import com.machine.sdk.common.model.response.PageResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "machine-data-service/machine-data-service/server/data/app_message_template",
        configuration = OpenFeignDefaultConfig.class)
public interface IDataAppMessageTemplateClient {

    @PostMapping("detail_by_type")
    AppMessageTemplateDetailOutputDto detailByType(@RequestBody @Validated AppMessageTemplateDetailByTypeInputDto inputDto);

    @PostMapping("page")
    PageResponse<AppMessageTemplateListOutPutDto> page(@RequestBody @Validated AppMessageTemplateQueryPageInputDto inputDto);

    @PostMapping("update_message_template")
    void updateMessageTemplate(@RequestBody @Validated AppMessageTemplateUpdateInputDto inputDto);

    @PostMapping("update_message_template_status")
    void updateMessageTemplateStatus(@RequestBody @Validated AppMessageTemplateUpdateStatusInputDto inputDto);

    @PostMapping("update_message_template_channel")
    void updateMessageTemplateChannel(@RequestBody @Validated AppMessageTemplateUpdateChannelInput inputDto);
}
