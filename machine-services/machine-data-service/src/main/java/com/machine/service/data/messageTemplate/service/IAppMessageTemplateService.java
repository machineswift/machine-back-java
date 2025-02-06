package com.machine.service.data.messageTemplate.service;


import com.machine.client.data.messageTemplate.dto.input.*;
import com.machine.client.data.messageTemplate.dto.output.AppMessageTemplateDetailOutputDto;
import com.machine.client.data.messageTemplate.dto.output.AppMessageTemplateListOutPutDto;
import com.machine.sdk.common.model.response.PageResponse;

public interface IAppMessageTemplateService {

    PageResponse<AppMessageTemplateListOutPutDto> page(AppMessageTemplateQueryPageInputDto request);

    AppMessageTemplateDetailOutputDto detailByType(AppMessageTemplateDetailByTypeInputDto request);

    void updateMessageTemplate(AppMessageTemplateUpdateInputDto inputDto);

    void updateMessageTemplateStatus(AppMessageTemplateUpdateStatusInputDto inputDto);

    void updateMessageTemplateChannel(AppMessageTemplateUpdateChannelInput inputDto);
}
