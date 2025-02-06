package com.machine.service.data.information.dao;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.machine.client.data.informaion.input.AppMessageGroupCountInputDto;
import com.machine.client.data.informaion.input.AppMessagePageInputDto;
import com.machine.client.data.informaion.input.AppMessagePageSuperInputDto;
import com.machine.client.data.informaion.input.AppMessageUnreadCountInputDto;
import com.machine.client.data.informaion.output.AppMessageGroupCountOutputDto;
import com.machine.client.data.informaion.output.AppMessageListOutputDto;
import com.machine.client.data.informaion.output.AppMessageListSuperOutputDto;
import com.machine.sdk.common.model.response.PageResponse;
import com.machine.service.data.information.dao.mapper.entity.AppMessageEntity;

import java.util.List;

public interface IAppMessageDao {
    Page<AppMessageListOutputDto> managePage(AppMessagePageInputDto inputDto);

    Page<AppMessageListSuperOutputDto> superPage(AppMessagePageSuperInputDto inputDto);

    void insert(AppMessageEntity appMessageEntity);

    void disposeMessage(String messageId);

    void readMessage(String messageId);

    List<AppMessageGroupCountOutputDto> groupCount(AppMessageGroupCountInputDto inputDto);

    Integer getUnreadCount(AppMessageUnreadCountInputDto inputDto);
}
