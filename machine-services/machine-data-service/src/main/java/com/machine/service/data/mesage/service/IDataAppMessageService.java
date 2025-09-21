package com.machine.service.data.mesage.service;


import com.machine.client.data.message.dto.output.AppMessageGroupCountOutputDto;
import com.machine.client.data.message.dto.output.AppMessageListOutputDto;
import com.machine.client.data.message.dto.output.AppMessageListSuperOutputDto;
import com.machine.client.data.message.dto.input.*;
import com.machine.sdk.common.model.response.PageResponse;

import java.util.List;

public interface IDataAppMessageService {

    PageResponse<AppMessageListOutputDto> managePage(AppMessagePageInputDto inputDto);

    PageResponse<AppMessageListSuperOutputDto> superPage(AppMessagePageSuperInputDto inputDto);

    Boolean saveMessageRecord(AppMessageSaveInputDto inputDto);

    Boolean sendMessage(AppMessageSendInputDto inputDto);

    Boolean readMessage(AppMessageReadInputDto inputDto);

    Boolean disposeMessage(AppMessageReadInputDto inputDto);

    List<AppMessageGroupCountOutputDto> groupCount(AppMessageGroupCountInputDto inputDto);

    Integer getUnreadCount(AppMessageUnreadCountInputDto inputDto);
}
