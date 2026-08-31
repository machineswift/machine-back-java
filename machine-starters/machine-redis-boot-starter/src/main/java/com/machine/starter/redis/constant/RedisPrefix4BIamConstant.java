package com.machine.starter.redis.constant;

/**
 * Redis Key 的前缀
 */
public class RedisPrefix4BIamConstant {

    public static class Auth {
        /**
         * 验证码
         */
        public static final String BIAM_AUTH_PIC_CAPTCHA = "biam:auth:pic_captcha:";

        /**
         * 短信验证码（手机号登录）
         */
        public static final String BIAM_AUTH_SMS_CAPTCHA_PHONE_LOGIN = "biam:auth:sms_captcha:phone_login:";

        /**
         * 短信验证码（忘记密码）
         */
        public static final String BIAM_AUTH_SMS_CAPTCHA_FORGET_PASSWORD = "biam:auth:sms_captcha:forget_password:";

        /**
         * auth token id
         */
        public static final String BIAM_AUTH_TOKEN_ID = "biam:auth:authTokenId:";
    }


    public static class UserFunctionPermission {
        /**
         * 用户功能权限 key
         */
        public static final String BIAM_USER_FUNCTION_PERMISSION_KEY = "biam:user:userFunctionPermission:key";

        /**
         * 用户功能权限 数据
         */
        public static final String BIAM_USER_FUNCTION_PERMISSION_DATA = "biam:user:userFunctionPermission:data:";
    }

    public static class Organization {

        /**
         * 组织树 key
         */
        public static final String BIAM_ORGANIZATION_TREE_KEY = "biam:organization:tree:key:";

        /**
         * 组织树 数据
         */
        public static final String BIAM_ORGANIZATION_TREE_DATA = "biam:organization:tree:data:";
    }

    public static class Permission {
        /**
         * 权限树 key
         */
        public static final String BIAM_PERMISSION_TREE_KEY = "biam:permission:tree:key";

        /**
         * 权限树 数据
         */
        public static final String BIAM_PERMISSION_TREE_DATA = "biam:permission:tree:data:";
    }


    public static class User {

        /**
         * 用户基本信息
         */
        public static final String BIAM_USER_BASE_KEY = "biam:user:base:key:";

    }

    public static class Auth2RegisteredClient {
        /**
         * 客户端id、版本号的MapKey
         */
        public static final String BIAM_IDENTITY_AUTH2_REGISTERED_CLIENT_VERSION_KEY = "biam:identity:auth2RegisteredClient:versionKey";

        /**
         * 客户端数据
         */
        public static final String BIAM_IDENTITY_AUTH2_REGISTERED_CLIENT_DATA = "biam:identity:auth2RegisteredClient:data:";
    }

    public static class UserSuperAppDataPermission {

        /**
         * 用户app数据功能权限 key
         */
        public static final String BIAM_USER_SUPER_APP_DATA_PERMISSION_KEY = "biam:user:userSuperAppDataPermission:key";

        /**
         * 用户 superApp 数据权限 数据
         */
        public static final String BIAM_USER_SUPER_APP_DATA_PERMISSION_DATA = "biam:user:userSuperAppDataPermission:data:";

    }

    public static class UserManageDataPermission {

        /**
         * 用户manage数据功能权限 key
         */
        public static final String BIAM_USER_MANAGE_DATA_PERMISSION_KEY = "biam:user:userManageDataPermission:key";

        /**
         * 用户manage数据权限 数据
         */
        public static final String BIAM_USER_MANAGE_DATA_PERMISSION_DATA = "biam:user:userManageDataPermission:data:";

    }

}
