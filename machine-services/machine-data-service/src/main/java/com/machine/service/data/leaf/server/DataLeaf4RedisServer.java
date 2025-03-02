package com.machine.service.data.leaf.server;

import com.machine.client.data.leaf.IDataLeaf4RedisClient;
import com.machine.sdk.common.envm.iam.organization.OrganizationTypeEnum;
import com.machine.service.data.leaf.service.ILeafService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Redis Key 生成服务
 */
@Slf4j
@RestController
@RequestMapping("server/data/leaf_4_redis")
public class DataLeaf4RedisServer implements IDataLeaf4RedisClient {

    @Autowired
    private ILeafService leafService;

    @Override
    @GetMapping("iam_user_functionPermission")
    public String iamUserFunctionPermission() {
        return leafService.getKqBatchNo(
                REDIS_IAM_USER,
                "FUNCTION_PERMISSION",
                8,
                200,
                "用户功能权限 redis key"
        );
    }

    @Override
    @GetMapping("iam_user_appDataPermission")
    public String iamUserAppDataPermissionPermission() {
        return leafService.getKqBatchNo(
                REDIS_IAM_USER,
                "APP_DATA_PERMISSION",
                8,
                200,
                "用户app数据权限 redis key"
        );
    }

    @Override
    @GetMapping("iam_permission_tree")
    public String iamPermissionTree() {
        return generateTreeKey(REDIS_IAM_PERMISSION, "权限树");
    }

    @Override
    @GetMapping("data_area_tree")
    public String dataAreaTree() {
        return generateTreeKey(REDIS_DATA_AREA, "区域树");
    }

    @Override
    @GetMapping("hrm_department_tree")
    public String hrmDepartmentTree() {
        return generateTreeKey(REDIS_HRM_DEPARTMENT, "部门树");
    }

    @Override
    @GetMapping("data_organization_tree")
    public String dataOrganizationTree(@RequestParam("organizationType") OrganizationTypeEnum organizationType) {
        return generateTreeKey(
                REDIS_DATA_ORGANIZATION + organizationType.getName() + "_",
                "组织树"
        );
    }

    /**
     * 生成树形结构的Redis key
     */
    private String generateTreeKey(String keyPrefix,
                                   String description) {
        return leafService.getKqBatchNo(
                keyPrefix,
                DEFAULT_TREE_PREFIX,
                DEFAULT_TREE_KEY_LENGTH,
                DEFAULT_TREE_EXPIRE_SECONDS,
                description + " redis key"
        );
    }

    /**
     * 默认树形结构前缀
     */
    private static final String DEFAULT_TREE_PREFIX = "TREE";
    /**
     * 默认树形结构key长度
     */
    private static final int DEFAULT_TREE_KEY_LENGTH = 6;
    /**
     * 默认树形结构过期时间(秒)
     */
    private static final int DEFAULT_TREE_EXPIRE_SECONDS = 10;


    /**
     * 用户功能权限
     */
    private static final String REDIS_IAM_USER = "REDIS_IAM_USER_";

    /**
     * 权限
     */
    private static final String REDIS_IAM_PERMISSION = "REDIS_IAM_PERMISSION_";

    /**
     * 区域
     */
    private static final String REDIS_DATA_AREA = "REDIS_DATA_AREA_";

    /**
     * 部门
     */
    private static final String REDIS_HRM_DEPARTMENT = "REDIS_DATA_DEPARTMENT_";

    /**
     * 组织
     */
    private static final String REDIS_DATA_ORGANIZATION = "REDIS_DATA_ORGANIZATION_";
}
