package com.machine.client.plugin.yunxi;

import com.machine.sdk.common.config.OpenFeignMaxTimeConfig;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name = "machine-plugin-service", path = "machine-plugin-service/server/plugin/yunxi_cube_shop",
        configuration = OpenFeignMaxTimeConfig.class)
public interface IYunXiCubeShopClient {

}



