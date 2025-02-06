package com.machine.test.temp.sdk;

import cn.hutool.json.JSONUtil;
import com.machine.sdk.beisen.client.employee.BeiSenEmployeeClient;
import com.machine.sdk.beisen.client.employee.dto.input.BeiSenEmployeeTimeWindowInputDto;
import com.machine.sdk.beisen.client.employee.dto.output.BeiSenEmployeeInfoOutputDto;
import com.machine.sdk.beisen.domain.BeiSenBaseResponse;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import okhttp3.OkHttpClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("sdk/beisen")
public class BeiSenController {

    @Resource(name = "beiSenOkHttpClient")
    private OkHttpClient okHttpClient;

    @GetMapping("employee_time")
    public void employeeTime() {
        String s = "{\"capacity\":300,\"enableTranslate\":true,\"isWithDeleted\":false,\"scrollId\":\"WzYxOTMzNzQ5NCwxNzE3Njc0Mzk1MDAwLDYyOTI4NTE1Nzk4NTY4MjA0XQ\",\"startTime\":\"2024-06-03T23:59:59\",\"stopTime\":\"2024-06-07T23:59:59\",\"timeWindowQueryType\":\"1\",\"withDisabled\":true}";
        BeiSenEmployeeTimeWindowInputDto inputDto = JSONUtil.toBean(s, BeiSenEmployeeTimeWindowInputDto.class);
        BeiSenBaseResponse<BeiSenEmployeeInfoOutputDto> response = BeiSenEmployeeClient.getByTimeWindow(okHttpClient, inputDto);
        List<BeiSenEmployeeInfoOutputDto> list = response.getData();
        log.info(JSONUtil.toJsonStr(response));

    }

}


