package com.machine.client.data.config;

import com.machine.sdk.base.config.OpenFeignMinTimeConfig;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name = "machine-data-service", path = "machine-data-service/server/data/openapi_config",
        configuration = OpenFeignMinTimeConfig.class)
public interface IDataOpenapiConfigClient {

}



