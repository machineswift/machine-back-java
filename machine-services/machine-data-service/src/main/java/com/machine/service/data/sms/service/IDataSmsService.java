package com.machine.service.data.sms.service;

import com.machine.client.data.sms.dto.input.DataSmsCountRecordInputDto;
import com.machine.client.data.sms.dto.input.DataSmsForgetPasswordInputDto;
import com.machine.client.data.sms.dto.input.DataSmsPhoneLoginInputDto;

public interface IDataSmsService {

    void send4PhoneLogin(DataSmsPhoneLoginInputDto inputDto);

    void send4ForgetPassword(DataSmsForgetPasswordInputDto inputDto);

    int countByCondition(DataSmsCountRecordInputDto inputDto);

}
