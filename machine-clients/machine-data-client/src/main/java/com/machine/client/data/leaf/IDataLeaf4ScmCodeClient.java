package com.machine.client.data.leaf;

import com.machine.sdk.base.config.OpenFeignMinTimeConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "machine-data-service", path = "machine-data-service/server/data/leaf_4_scm",
        configuration = OpenFeignMinTimeConfig.class)
public interface IDataLeaf4ScmCodeClient {

    /**
     * 后台分类编码
     */
    @GetMapping("back_category_code")
    String backCategoryCode();

    /**
     * 前台分类编码
     */
    @GetMapping("front_category_code")
    String frontCategoryCode();

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



