package com.machine.service.data.mesage.service;


import com.machine.client.data.message.dto.input.*;
import com.machine.client.data.message.dto.output.AppMessageTemplateDetailOutputDto;
import com.machine.client.data.message.dto.output.AppMessageTemplateListOutPutDto;
import com.machine.sdk.base.model.response.PageResponse;

public interface IDataAppMessageTemplateService {

    void updateMessageTemplate(AppMessageTemplateUpdateInputDto inputDto);

    void updateMessageTemplateStatus(AppMessageTemplateUpdateStatusInputDto inputDto);

    void updateMessageTemplateChannel(AppMessageTemplateUpdateChannelInput inputDto);

    AppMessageTemplateDetailOutputDto detailByType(AppMessageTemplateDetailByTypeInputDto request);

    PageResponse<AppMessageTemplateListOutPutDto> page(AppMessageTemplateQueryPageInputDto request);
}
