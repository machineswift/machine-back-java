package com.machine.service.data.sms.server;

import cn.hutool.json.JSONUtil;
import com.machine.client.data.sms.IDataSmsClient;
import com.machine.client.data.sms.dto.input.DataSmsCountRecordInputDto;
import com.machine.client.data.sms.dto.input.DataSmsForgetPasswordInputDto;
import com.machine.client.data.sms.dto.input.DataSmsPhoneLoginInputDto;
import com.machine.service.data.sms.service.IDataSmsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("server/data/sms")
public class DataSmsServer implements IDataSmsClient {

    @Autowired
    private IDataSmsService smsService;

    @Override
    @PostMapping("send_4_phoneLogin")
    public void send4PhoneLogin(@RequestBody @Validated DataSmsPhoneLoginInputDto inputDto) {
        log.info("手机号登录发送短信，inputDto={}", JSONUtil.toJsonStr(inputDto));
        smsService.send4PhoneLogin(inputDto);
    }

    @Override
    @PostMapping("send_4_forgetPassword")
    public void send4ForgetPassword(@RequestBody @Validated DataSmsForgetPasswordInputDto inputDto) {
        log.info("忘记密码发送短信，inputDto={}", JSONUtil.toJsonStr(inputDto));
        smsService.send4ForgetPassword(inputDto);
    }

    @Override
    @PostMapping("count_by_condition")
    public int countByCondition(@RequestBody @Validated DataSmsCountRecordInputDto inputDto) {
        return smsService.countByCondition(inputDto);
    }

}
