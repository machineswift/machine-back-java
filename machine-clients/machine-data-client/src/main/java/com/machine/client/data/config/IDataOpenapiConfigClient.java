package com.machine.client.data.config;

import com.machine.sdk.common.config.OpenFeignDefaultConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "machine-data-service/machine-data-service/server/data/openapi_config",
        configuration = OpenFeignDefaultConfig.class)
public interface IDataOpenapiConfigClient {

    @GetMapping("resource_blank_ip")
    String resourceBlankIp();

}
