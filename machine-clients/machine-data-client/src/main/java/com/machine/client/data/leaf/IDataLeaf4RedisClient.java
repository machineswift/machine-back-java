package com.machine.client.data.leaf;

import com.machine.sdk.common.config.OpenFeignMinTimeConfig;
import com.machine.sdk.common.envm.data.tag.ProfileSubjectTypeEnum;
import com.machine.sdk.common.envm.iam.organization.IamOrganizationTypeEnum;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "machine-data-service", path = "machine-data-service/server/data/leaf_4_redis",
        configuration = OpenFeignMinTimeConfig.class)
public interface IDataLeaf4RedisClient {

    /**
     * 用户功能权限的redis动态key
     */
    @GetMapping("iam_user_functionPermission")
    String iamUserFunctionPermission();


    /**
     * 用户数据权限的redis动态key
     */
    @GetMapping("iam_user_dataPermission")
    String iamUserDataPermissionPermission();


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
     * 素材分类树数据的redis动态key
     */
    @GetMapping("data_material_category_tree")
    String dataMaterialCategoryTree();

    /**
     * 附件分类树数据的redis动态key
     */
    @GetMapping("data_attachment_category_tree")
    String dataAttachmentCategoryTree();

    /**
     * 组织树数据的redis动态key
     */
    @GetMapping("data_organization_tree")
    String dataOrganizationTree(@RequestParam("type") IamOrganizationTypeEnum type);

    /**
     * 智能标签分类树数据的redis动态key
     */
    @GetMapping("data_tag_category_tree")
    String dataTagCategoryTree(@RequestParam("type") ProfileSubjectTypeEnum type);
}



