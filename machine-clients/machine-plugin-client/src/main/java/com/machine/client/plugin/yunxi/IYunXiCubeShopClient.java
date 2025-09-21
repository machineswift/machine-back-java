package com.machine.client.plugin.yunxi;

import com.machine.sdk.common.config.OpenFeignLongTimeConfig;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name = "machine-plugin-service/machine-plugin-service/server/plugin/yunxi_cube_shop",
        configuration = OpenFeignLongTimeConfig.class)
public interface IYunXiCubeShopClient {

}
