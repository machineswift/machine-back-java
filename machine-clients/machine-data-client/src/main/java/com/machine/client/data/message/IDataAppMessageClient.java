package com.machine.client.data.message;

import com.machine.client.data.message.dto.output.AppMessageGroupCountOutputDto;
import com.machine.client.data.message.dto.output.AppMessageListOutputDto;
import com.machine.client.data.message.dto.output.AppMessageListSuperOutputDto;
import com.machine.client.data.message.dto.input.*;
import com.machine.sdk.common.config.OpenFeignDefaultConfig;
import com.machine.sdk.common.model.response.PageResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(name = "machine-data-service/machine-data-service/server/data/app_message", configuration = OpenFeignDefaultConfig.class)
public interface IDataAppMessageClient {

    @PostMapping("manage_page")
    PageResponse<AppMessageListOutputDto> managePage(@RequestBody AppMessagePageInputDto request);

    @PostMapping("super_page")
    PageResponse<AppMessageListSuperOutputDto> superPage(@RequestBody @Validated AppMessagePageSuperInputDto inputDto);

    @PostMapping("save")
    Boolean saveMessageRecord(@RequestBody @Validated AppMessageSaveInputDto request);

    @PostMapping("send")
    Boolean sendMessage(@RequestBody @Validated AppMessageSendInputDto request);

    @PostMapping("read")
    Boolean readMessage(@RequestBody @Validated AppMessageReadInputDto request);

    @PostMapping("dispose")
    Boolean disposeMessage(@RequestBody @Validated AppMessageReadInputDto request);

    @PostMapping("group_count")
    List<AppMessageGroupCountOutputDto> groupCount(@RequestBody AppMessageGroupCountInputDto countInputDto);

    @PostMapping("get_unread_count")
    Integer getUnreadCount(@RequestBody AppMessageUnreadCountInputDto inputDto);
}
