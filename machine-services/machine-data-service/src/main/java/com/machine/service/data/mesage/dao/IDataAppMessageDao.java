package com.machine.service.data.mesage.dao;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.machine.client.data.message.dto.input.AppMessageGroupCountInputDto;
import com.machine.client.data.message.dto.input.AppMessagePageInputDto;
import com.machine.client.data.message.dto.input.AppMessagePageSuperInputDto;
import com.machine.client.data.message.dto.input.AppMessageUnreadCountInputDto;
import com.machine.client.data.message.dto.output.AppMessageGroupCountOutputDto;
import com.machine.client.data.message.dto.output.AppMessageListOutputDto;
import com.machine.client.data.message.dto.output.AppMessageListSuperOutputDto;
import com.machine.service.data.mesage.dao.mapper.entity.DataAppMessageEntity;

import java.util.List;

public interface IDataAppMessageDao {
    Page<AppMessageListOutputDto> managePage(AppMessagePageInputDto inputDto);

    Page<AppMessageListSuperOutputDto> superPage(AppMessagePageSuperInputDto inputDto);

    void insert(DataAppMessageEntity dataAppMessageEntity);

    void disposeMessage(String messageId);

    void readMessage(String messageId);

    List<AppMessageGroupCountOutputDto> groupCount(AppMessageGroupCountInputDto inputDto);

    Integer getUnreadCount(AppMessageUnreadCountInputDto inputDto);
}
