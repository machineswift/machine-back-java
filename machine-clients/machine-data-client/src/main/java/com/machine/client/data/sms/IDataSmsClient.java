package com.machine.client.data.sms;

import com.machine.client.data.sms.dto.input.DataSmsCountRecordInputDto;
import com.machine.client.data.sms.dto.input.DataSmsForgetPasswordInputDto;
import com.machine.client.data.sms.dto.input.DataSmsPhoneLoginInputDto;
import com.machine.sdk.common.config.OpenFeignMinTimeConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "machine-data-service", path = "machine-data-service/server/data/sms",
        configuration = OpenFeignMinTimeConfig.class)
public interface IDataSmsClient {

    @PostMapping("send_4_phoneLogin")
    void send4PhoneLogin(@RequestBody @Validated DataSmsPhoneLoginInputDto inputDto);

    @PostMapping("send_4_forgetPassword")
    void send4ForgetPassword(@RequestBody @Validated DataSmsForgetPasswordInputDto inputDto);

    @PostMapping("count_by_condition")
    int countByCondition(@RequestBody @Validated DataSmsCountRecordInputDto inputDto);
}



