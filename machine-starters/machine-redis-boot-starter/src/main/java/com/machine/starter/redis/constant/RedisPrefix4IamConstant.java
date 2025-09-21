package com.machine.starter.redis.constant;

/**
 * Redis Key 的前缀
 */
public class RedisPrefix4IamConstant {
    public static class Auth2 {
        /**
         * state
         */
        public static final String IAM_AUTH2_STATE = "iam:auth2:state:";
    }

    public static class Auth {
        /**
         * 验证码
         */
        public static final String IAM_AUTH_PIC_CAPTCHA = "iam:auth:pic_captcha:";

        /**
         * 短信验证码（手机号登录）
         */
        public static final String IAM_AUTH_SMS_CAPTCHA_PHONE_LOGIN = "iam:auth:sms_captcha:phone_login:";

        /**
         * 短信验证码（忘记密码）
         */
        public static final String IAM_AUTH_SMS_CAPTCHA_FORGET_PASSWORD = "iam:auth:sms_captcha:forget_password:";

        /**
         * auth token id
         */
        public static final String IAM_AUTH_TOKEN_ID = "iam:auth:authTokenId:";
    }

    public static class Organization{

        /**
         * 组织树 key
         */
        public static final String DATA_ORGANIZATION_TREE_KEY = "iam:organization:tree:key:";

        /**
         * 组织树 数据
         */
        public static final String DATA_ORGANIZATION_TREE_DATA = "iam:organization:tree:data:";
    }

    public static class Permission {
        /**
         *  权限树 key
         */
        public static final String IAM_PERMISSION_TREE_KEY = "iam:permission:tree:key";

        /**
         *  权限树 数据
         */
        public static final String IAM_PERMISSION_TREE_DATA = "iam:permission:tree:data:";
    }


    public static class User {

        /**
         * 用户基本信息
         */
        public static final String IAM_USER_BASE_KEY = "iam:user:base:key:";

    }

    public static class UserFunctionPermission {
        /**
         * 用户功能权限 key
         */
        public static final String IAM_USER_FUNCTION_PERMISSION_KEY = "iam:user:userFunctionPermission:key";

        /**
         * 用户功能权限 数据
         */
        public static final String IAM_USER_FUNCTION_PERMISSION_DATA = "iam:user:userFunctionPermission:data:";
    }

    public static class UserSuperAppDataPermission {

        /**
         * 用户app数据功能权限 key
         */
        public static final String IAM_USER_SUPER_APP_DATA_PERMISSION_KEY = "iam:user:userSuperAppDataPermission:key";

        /**
         * 用户 superApp 数据权限 数据
         */
        public static final String IAM_USER_SUPER_APP_DATA_PERMISSION_DATA = "iam:user:userSuperAppDataPermission:data:";

    }

    public static class UserManageDataPermission {

        /**
         * 用户manage数据功能权限 key
         */
        public static final String IAM_USER_MANAGE_DATA_PERMISSION_KEY = "iam:user:userManageDataPermission:key";

        /**
         * 用户manage数据权限 数据
         */
        public static final String IAM_USER_MANAGE_DATA_PERMISSION_DATA = "iam:user:userManageDataPermission:data:";

    }

}
