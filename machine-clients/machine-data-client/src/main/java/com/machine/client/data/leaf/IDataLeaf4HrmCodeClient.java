package com.machine.client.data.leaf;

import com.machine.sdk.common.config.OpenFeignMinTimeConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "machine-data-service", path = "machine-data-service/server/data/leaf_4_hrm",
        configuration = OpenFeignMinTimeConfig.class)
public interface IDataLeaf4HrmCodeClient {

    /**
     * 门店员工编码
     */
    @GetMapping("shop_employee_code")
    String shopEmployeeCode();
}



