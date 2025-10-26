package com.machine.client.data.leaf;

import com.machine.sdk.common.config.OpenFeignMinTimeConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "machine-data-service", path = "machine-data-service/server/data/leaf_4_crm",
        configuration = OpenFeignMinTimeConfig.class)
public interface IDataLeaf4CrmCodeClient {

    /**
     * 客户编码
     */
    @GetMapping("customer_code")
    String customerCode();

    /**
     * 会员编码
     */
    @GetMapping("member_code")
    String memberCode();

}



