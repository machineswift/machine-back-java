package com.machine.client.scm.test;

import com.machine.sdk.common.config.OpenFeignMinTimeConfig;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name = "machine-scm-service", path = "machine-scm-service/server/scm/test",
        configuration = OpenFeignMinTimeConfig.class)
public interface IScmTestClient {


}



