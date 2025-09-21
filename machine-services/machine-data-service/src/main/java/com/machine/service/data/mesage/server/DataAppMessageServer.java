package com.machine.service.data.mesage.server;

import com.machine.client.data.message.IDataAppMessageClient;
import com.machine.client.data.message.dto.output.AppMessageGroupCountOutputDto;
import com.machine.client.data.message.dto.output.AppMessageListOutputDto;
import com.machine.client.data.message.dto.output.AppMessageListSuperOutputDto;
import com.machine.client.data.message.dto.input.*;
import com.machine.sdk.common.model.response.PageResponse;
import com.machine.service.data.mesage.service.IDataAppMessageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("server/data/app_message")
public class DataAppMessageServer implements IDataAppMessageClient {

    @Autowired
    private IDataAppMessageService appMessageService;

    @Override
    @PostMapping("manage_page")
    public PageResponse<AppMessageListOutputDto> managePage(@RequestBody @Validated AppMessagePageInputDto inputDto) {
        return appMessageService.managePage(inputDto);
    }

    @Override
    @PostMapping("super_page")
    public PageResponse<AppMessageListSuperOutputDto> superPage(@RequestBody @Validated AppMessagePageSuperInputDto inputDto) {
        return appMessageService.superPage(inputDto);
    }

    @Override
    @PostMapping("save")
    public Boolean saveMessageRecord(@RequestBody @Validated AppMessageSaveInputDto inputDto) {
        return appMessageService.saveMessageRecord(inputDto);
    }

    @Override
    @PostMapping("send")
    public Boolean sendMessage(@RequestBody @Validated AppMessageSendInputDto inputDto) {
        return appMessageService.sendMessage(inputDto);
    }

    @Override
    @PostMapping("read")
    public Boolean readMessage(@RequestBody @Validated AppMessageReadInputDto inputDto) {
        return appMessageService.readMessage(inputDto);
    }

    @Override
    @PostMapping("dispose")
    public Boolean disposeMessage(@RequestBody @Validated AppMessageReadInputDto inputDto) {
        return appMessageService.disposeMessage(inputDto);
    }

    @Override
    @PostMapping("group_count")
    public List<AppMessageGroupCountOutputDto> groupCount(@RequestBody AppMessageGroupCountInputDto inputDto) {
        return appMessageService.groupCount(inputDto);
    }

    @Override
    @PostMapping("get_unread_count")
    public Integer getUnreadCount(@RequestBody AppMessageUnreadCountInputDto inputDto) {
        return appMessageService.getUnreadCount(inputDto);
    }
}
