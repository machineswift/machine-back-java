package com.machine.client.data.leaf;

import com.machine.sdk.common.config.OpenFeignDefaultConfig;
import com.machine.sdk.common.envm.iam.OrganizationTypeEnum;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "machine-data-service/machine-data-service/server/data/leaf_4_redis",
        configuration = OpenFeignDefaultConfig.class)
public interface IDataLeaf4RedisClient {

    /**
     * 用户功能权限的redis动态key
     */
    @GetMapping("iam_user_functionPermission")
    String iamUserFunctionPermission();


    /**
     * 用户app数据权限的redis动态key
     */
    @GetMapping("iam_user_appDataPermission")
    String iamUserAppDataPermissionPermission();


    /**
     * 权限树数据的redis动态key
     */
    @GetMapping("iam_permission_tree")
    String iamPermissionTree();

    /**
     * 区域树数据的redis动态key
     */
    @GetMapping("data_area_tree")
    String dataAreaTree();

    /**
     * 部门树数据的redis动态key
     */
    @GetMapping("hrm_department_tree")
    String hrmDepartmentTree();

    /**
     * 组织树数据的redis动态key
     */
    @GetMapping("data_organization_tree")
    String dataOrganizationTree(@RequestParam("organizationType") OrganizationTypeEnum organizationType);
}
