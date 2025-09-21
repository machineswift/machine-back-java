package com.machine.client.scm.test;

import com.machine.sdk.common.config.OpenFeignDefaultConfig;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name = "machine-scm-service/machine-scm-service/server/scm/test",
        configuration = OpenFeignDefaultConfig.class)
public interface IScmTestClient {


}
