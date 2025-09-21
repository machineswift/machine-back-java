package com.machine.sdk.common.constant;

public class CommonIamConstant {
    /**
     * 数据权限默认功能编码
     */
    public static final String DATA_PERMISSION_DEFAULT_FUNCTION_CODE = "DEFAULT";

    /**
     * 数据权限默认功能名称
     */
    public static final String DATA_PERMISSION_DEFAULT_FUNCTION_NAME = "默认";


    public static class Area {
        /**
         * root 用户父亲ID
         */
        public static final String ROOT_AREA_PARENT_ID = "root";
    }


    public static class User {
        /**
         * root 用户ID
         */
        public static final String ROOT_USER_ID = "root";
    }

    public static class Organization {
        /**
         * 根组织父Id
         */
        public static final String ORGANIZATION_ROOT_PARENT_ID = "root";

        /**
         * 虚拟组织Id【未分配】
         */
        public static final String ORGANIZATION_VIRTUAL_NODE = "organization_virtual_node";

        /**
         * 虚拟组织名称【未分配】
         */
        public static final String ORGANIZATION_VIRTUAL_NODE_NAME = "未分配";
    }
}
