package com.machine.starter.redis.constant;

/**
 * Redis 分布式锁 的前缀
 */
public class RedisLockPrefixConstant {

    public static class Iam {

        /**
         * 权限树
         */
        public static final String LOCK_IAM_PERMISSION_TREE = "lock:data:permission:tree:";

        /**
         * 用户基本信息
         */
        public static final String LOCK_IAM_USER_BASE = "lock:iam:user:base:";

        /**
         * 用户功能权限
         */
        public static final String LOCK_IAM_USER_FUNCTION_PERMISSION = "lock:iam:user:userFunctionPermission";

        /**
         * 用户app数据权限
         */
        public static final String LOCK_IAM_USER_APP_DATA_PERMISSION = "lock:iam:user:userAppDataPermission";

        /**
         * 用户app数据权限
         */
        public static final String LOCK_IAM_USER_MANAGE_DATA_PERMISSION = "lock:iam:user:userManageDataPermission";


        /**
         * 手机号登录短信验证码（获取验证码）
         */
        public static final String LOCK_IAM_AUTH_SMS_CAPTCHA_PHONE_LOGIN_GET_CAPTCHA =
                "lock:iam:auth:sms_captcha:phone_login_get_captcha:";

        /**
         * 手机号登录短信验证码（提交）
         */
        public static final String LOCK_IAM_AUTH_SMS_CAPTCHA_PHONE_LOGIN_SUBMIT =
                "lock:iam:auth:sms_captcha:phone_login_submit:";

        /**
         * 忘记密码短信验证码（获取验证码）
         */
        public static final String LOCK_IAM_AUTH_SMS_CAPTCHA_FORGET_PASSWORD_GET_CAPTCHA =
                "lock:iam:auth:sms_captcha:forget_password_get_captcha:";

        /**
         * 忘记密码短信验证码（修改密码）
         */
        public static final String LOCK_IAM_AUTH_SMS_CAPTCHA_FORGET_PASSWORD_UPDATE_PASSWORD =
                "lock:iam:auth:sms_captcha:forget_password_update_password:";


    }

    public static class Data {

        /**
         * 区域树
         */
        public static final String LOCK_DATA_AREA_TREE = "lock:data:area:tree:";

        /**
         * 组织树
         */
        public static final String LOCK_DATA_ORGANIZATION_TREE = "lock:data:organization:tree:";

        /**
         * 素材地址
         */
        public static final String LOCK_DATA_MATERIAL_URL =  "lock:data:material:url:";

        /**
         * 素材缩略图地址
         */
        public static final String LOCK_DATA_MATERIAL_THUMBNAIL_URL =  "lock:data:material:thumbnailUrl:";

        /**
         * 素材分类
         */
        public static final String LOCK_DATA_MATERIAL_CATEGORY_TREE = "lock:data:materialCategory:tree";


        /**
         * 附件地址
         */
        public static final String LOCK_DATA_ATTACHMENT_URL =  "lock:data:attachment:url:";

        /**
         * 附件缩略图地址
         */
        public static final String LOCK_DATA_ATTACHMENT_THUMBNAIL_URL =  "lock:data:attachment:thumbnailUrl:";

        /**
         * 智能标签分类
         */
        public static final String LOCK_DATA_TAG_CATEGORY_TREE = "lock:data:tagCategory:tree:";

    }

    public static class Hrm {
        /**
         * 部门树
         */
        public static final String LOCK_HRM_DEPARTMENT_TREE = "lock:hrm:department:tree:";
    }

}
