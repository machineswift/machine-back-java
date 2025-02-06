package com.machine.client.hrm.employee;

import com.machine.sdk.common.config.OpenFeignLongTimeConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "machine-hrm-service/machine-hrm-service/server/hrm/employee_long_time",
        configuration = OpenFeignLongTimeConfig.class)
public interface IHrmEmployeeLongTimeClient {

    @GetMapping("sync")
    void sync();

}
