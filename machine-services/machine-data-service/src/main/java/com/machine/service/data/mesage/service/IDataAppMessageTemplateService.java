package com.machine.service.data.mesage.service;


import com.machine.client.data.message.dto.input.*;
import com.machine.client.data.message.dto.output.AppMessageTemplateDetailOutputDto;
import com.machine.client.data.message.dto.output.AppMessageTemplateListOutPutDto;
import com.machine.sdk.common.model.response.PageResponse;

public interface IDataAppMessageTemplateService {

    PageResponse<AppMessageTemplateListOutPutDto> page(AppMessageTemplateQueryPageInputDto request);

    AppMessageTemplateDetailOutputDto detailByType(AppMessageTemplateDetailByTypeInputDto request);

    void updateMessageTemplate(AppMessageTemplateUpdateInputDto inputDto);

    void updateMessageTemplateStatus(AppMessageTemplateUpdateStatusInputDto inputDto);

    void updateMessageTemplateChannel(AppMessageTemplateUpdateChannelInput inputDto);
}
