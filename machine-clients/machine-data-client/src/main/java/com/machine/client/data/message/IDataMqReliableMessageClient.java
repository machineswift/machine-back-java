package com.machine.client.data.message;

import com.machine.client.data.message.dto.input.MqReliableMessageCreateInputDto;
import com.machine.client.data.message.dto.input.MqReliableMessageUpdate4ExceptionInputDto;
import com.machine.client.data.message.dto.output.MqReliableMessageDetailDto;
import com.machine.sdk.common.config.OpenFeignDefaultConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "machine-data-service/machine-data-service/server/data/mq_reliable_message", configuration = OpenFeignDefaultConfig.class)
public interface IDataMqReliableMessageClient {

    @PostMapping("create")
    void create(@RequestBody @Validated MqReliableMessageCreateInputDto inputDto);

    @GetMapping("delete")
    void delete(@RequestParam("id") String id);

    @PostMapping("update")
    void update4Exception(@RequestBody @Validated MqReliableMessageUpdate4ExceptionInputDto inputDto);

    @GetMapping("resendMessage")
    void resendMessage();

    @GetMapping("contain")
    Boolean contain(@RequestParam("id") String id);

    @GetMapping("getById")
    MqReliableMessageDetailDto getById(@RequestParam("id") String id);
}
