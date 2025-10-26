package com.machine.client.data.config;

import com.machine.sdk.common.config.OpenFeignMinTimeConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "machine-data-service", path = "machine-data-service/server/data/openapi_config",
        configuration = OpenFeignMinTimeConfig.class)
public interface IDataOpenapiConfigClient {

    @GetMapping("resource_blank_ip")
    String resourceBlankIp();

}



