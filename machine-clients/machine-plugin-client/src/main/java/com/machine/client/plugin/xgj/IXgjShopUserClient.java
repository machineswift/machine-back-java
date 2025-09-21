package com.machine.client.plugin.xgj;

import com.machine.sdk.common.config.OpenFeignLongTimeConfig;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name = "machine-plugin-service/machine-plugin-service/server/plugin/xgj_shop_user",
        configuration = OpenFeignLongTimeConfig.class)
public interface IXgjShopUserClient {
}
