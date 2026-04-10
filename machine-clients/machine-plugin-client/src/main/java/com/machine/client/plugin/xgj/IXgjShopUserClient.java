package com.machine.client.plugin.xgj;

import com.machine.sdk.base.config.OpenFeignMaxTimeConfig;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name = "machine-plugin-service", path = "machine-plugin-service/server/plugin/xgj_shop_user",
        configuration = OpenFeignMaxTimeConfig.class)
public interface IXgjShopUserClient {
}



