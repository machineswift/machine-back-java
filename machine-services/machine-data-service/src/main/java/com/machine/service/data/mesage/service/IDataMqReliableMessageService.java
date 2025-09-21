package com.machine.service.data.mesage.service;


import com.machine.client.data.message.dto.input.MqReliableMessageCreateInputDto;
import com.machine.client.data.message.dto.input.MqReliableMessageUpdate4ExceptionInputDto;
import com.machine.client.data.message.dto.output.MqReliableMessageDetailDto;

public interface IDataMqReliableMessageService {

    void create(MqReliableMessageCreateInputDto inputDto);

    void delete(String id);

    void update4Exception(MqReliableMessageUpdate4ExceptionInputDto inputDto);

    void resendMessage();

    Boolean contain(String id);

    MqReliableMessageDetailDto getById(String id);

}
