package com.machine.client.ai.test;

import com.machine.sdk.common.config.OpenFeignMinTimeConfig;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name = "machine-ai-service",  path = "machine-ai-service/server/ai/test",
        configuration = OpenFeignMinTimeConfig.class)
public interface IAiTestClient {


}



