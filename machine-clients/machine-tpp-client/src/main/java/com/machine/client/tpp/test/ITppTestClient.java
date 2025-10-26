package com.machine.client.tpp.test;

import com.machine.sdk.common.config.OpenFeignMinTimeConfig;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name = "machine-tpp-service", path = "machine-tpp-service/server/tpp/test",
        configuration = OpenFeignMinTimeConfig.class)
public interface ITppTestClient {


}



