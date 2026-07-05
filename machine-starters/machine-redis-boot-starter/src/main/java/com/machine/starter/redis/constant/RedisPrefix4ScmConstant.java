package com.machine.starter.redis.constant;

/**
 * Redis Key 的前缀
 */
public class RedisPrefix4ScmConstant {

    public static class BackCategory {
        /**
         *  后台分类树 key
         */
        public static final String SCM_BACK_CATEGORY_TREE_KEY = "scm:backCategory:tree:key";

        /**
         * 后台分类树 数据
         */
        public static final String SCM_BACK_CATEGORY_TREE_DATA = "scm:backCategory:tree:data:";
    }

    public static class FrontCategory {
        /**
         *  前台分类树 key
         */
        public static final String SCM_FRONT_CATEGORY_TREE_KEY = "scm:frontCategory:tree:key";

        /**
         * 前台分类树 数据
         */
        public static final String SCM_FRONT_CATEGORY_TREE_DATA = "scm:frontCategory:tree:data:";
    }

}
