package com.machine.client.tpp.test;

import com.machine.sdk.common.config.OpenFeignDefaultConfig;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name = "machine-tpp-service/machine-tpp-service/server/tpp/test",
        configuration = OpenFeignDefaultConfig.class)
public interface ITppTestClient {


}
