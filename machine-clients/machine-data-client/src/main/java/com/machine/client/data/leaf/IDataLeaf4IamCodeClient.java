package com.machine.client.data.leaf;

import com.machine.sdk.common.config.OpenFeignDefaultConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "machine-data-service/machine-data-service/server/data/leaf_4_iam",
        configuration = OpenFeignDefaultConfig.class)
public interface IDataLeaf4IamCodeClient {

    /**
     * 组织编码
     */
    @GetMapping("organization_code")
    String organizationCode();

    /**
     * 角色编码
     */
    @GetMapping("role_code")
    String roleCode();

    /**
     * 用户编码
     */
    @GetMapping("user_code")
    String userCode();
}
