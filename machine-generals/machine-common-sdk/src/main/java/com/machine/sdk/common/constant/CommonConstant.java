package com.machine.sdk.common.constant;

public class CommonConstant {
    /**
     * 冒号 分隔符
     */
    public static final char SEPARATOR_COLON = ':';

    /**
     * 点 分隔符
     */
    public static final char SEPARATOR_PERIOD = '.';

    /**
     * 下划线 分隔符
     */
    public static final char SEPARATOR_UNDERSCORE = '_';

    /**
     * 短横线 分隔符
     */
    public static final char SEPARATOR_HYPHEN = '-';


    public static class Iam {
        /**
         * root 用户ID
         */
        public static final String ROOT_USER_ID = "root";
    }

    public static class Data {
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

    public static class Shop {
        /**
         * 标签编码前缀
         */
        public static final String BIAO_QIAN_PREFIX = "BQ";

        /**
         * 门店标签Id
         */
        public static final String SHOP_LABEL_ID = "shop_label";
    }

    public static class Hrm {
        /**
         * 根部门父Id
         */
        public static final String DEPARTMENT_ROOT_PARENT_ID = "-2";
    }

}
