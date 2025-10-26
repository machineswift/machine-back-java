package com.machine.client.plugin.xgj;

import com.machine.sdk.common.config.OpenFeignMaxTimeConfig;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name = "machine-plugin-service", path = "machine-plugin-service/server/plugin/xgj_shop_user",
        configuration = OpenFeignMaxTimeConfig.class)
public interface IXgjShopUserClient {
}



