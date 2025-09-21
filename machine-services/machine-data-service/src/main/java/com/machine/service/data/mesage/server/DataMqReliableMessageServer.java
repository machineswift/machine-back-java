package com.machine.service.data.mesage.server;

import cn.hutool.json.JSONUtil;
import com.machine.client.data.message.IDataMqReliableMessageClient;
import com.machine.client.data.message.dto.input.MqReliableMessageCreateInputDto;
import com.machine.client.data.message.dto.input.MqReliableMessageUpdate4ExceptionInputDto;
import com.machine.client.data.message.dto.output.MqReliableMessageDetailDto;
import com.machine.service.data.mesage.service.IDataMqReliableMessageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("server/data/mq_reliable_message")
public class DataMqReliableMessageServer implements IDataMqReliableMessageClient {

    @Autowired
    private IDataMqReliableMessageService mqMessageService;

    @Override
    @PostMapping("create")
    public void create(@RequestBody MqReliableMessageCreateInputDto inputDto) {
        log.info("新增可靠消息，inputDto={}", JSONUtil.toJsonStr(inputDto));
        mqMessageService.create(inputDto);
    }

    @Override
    @GetMapping("delete")
    public void delete(@RequestParam("id") String id) {
        log.info("删除可靠消息，id={}", id);
        mqMessageService.delete(id);
    }

    @Override
    @PostMapping("update")
    public void update4Exception(@RequestBody MqReliableMessageUpdate4ExceptionInputDto inputDto) {
        log.info("修改可靠消息，inputDto={}", JSONUtil.toJsonStr(inputDto));
        mqMessageService.update4Exception(inputDto);
    }

    @GetMapping("resendMessage")
    @Override
    public void resendMessage() {
        log.info("定时任务重新发送可靠消息");
        mqMessageService.resendMessage();
    }

    @Override
    @GetMapping("contain")
    public Boolean contain(@RequestParam("id") String id) {
        return mqMessageService.contain(id);
    }

    @Override
    @GetMapping("getById")
    public MqReliableMessageDetailDto getById(@RequestParam("id") String id) {
        return mqMessageService.getById(id);
    }
}
