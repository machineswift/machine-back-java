package com.machine.service.data.information.service;


import com.machine.client.data.informaion.input.*;
import com.machine.client.data.informaion.output.AppMessageGroupCountOutputDto;
import com.machine.client.data.informaion.output.AppMessageListOutputDto;
import com.machine.client.data.informaion.output.AppMessageListSuperOutputDto;
import com.machine.sdk.common.model.response.PageResponse;

import java.util.List;

public interface IAppMessageService {

    PageResponse<AppMessageListOutputDto> managePage(AppMessagePageInputDto inputDto);

    PageResponse<AppMessageListSuperOutputDto> superPage(AppMessagePageSuperInputDto inputDto);

    Boolean saveMessageRecord(AppMessageSaveInputDto inputDto);

    Boolean sendMessage(AppMessageSendInputDto inputDto);

    Boolean readMessage(AppMessageReadInputDto inputDto);

    Boolean disposeMessage(AppMessageReadInputDto inputDto);

    List<AppMessageGroupCountOutputDto> groupCount(AppMessageGroupCountInputDto inputDto);

    Integer getUnreadCount(AppMessageUnreadCountInputDto inputDto);
}
