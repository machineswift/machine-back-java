package com.machine.client.data.leaf;

import com.machine.sdk.common.config.OpenFeignMinTimeConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "machine-data-service", path = "machine-data-service/server/data/leaf_4_scm",
        configuration = OpenFeignMinTimeConfig.class)
public interface IDataLeaf4ScmCodeClient {

    /**
     * 供应商编码
     */
    @GetMapping("supplier_code")
    String supplierCode();

    /**
     * 加盟商编码
     */
    @GetMapping("franchisee_code")
    String franchiseeCode();

}



