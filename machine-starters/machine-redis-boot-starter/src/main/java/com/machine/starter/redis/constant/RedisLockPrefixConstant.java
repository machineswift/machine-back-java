package com.machine.starter.redis.constant;

/**
 * Redis 分布式锁 的前缀
 */
public class RedisLockPrefixConstant {

    public static class Iam {

        /**
         * 权限树
         */
        public static final String IAM_PERMISSION_TREE_LOCK = "data:permission:tree:lock:";

        /**
         * 用户基本信息
         */
        public static final String IAM_USER_BASE_LOCK = "iam:user:base:lock:";

        /**
         * 用户功能权限
         */
        public static final String IAM_USER_FUNCTION_PERMISSION_LOCK = "iam:user:userFunctionPermission:lock";

        /**
         * 用户app数据功能权限
         */
        public static final String IAM_USER_APP_DATA_PERMISSION_LOCK = "iam:user:userAppDataPermission:lock";

        /**
         * 手机号登录短信验证码（获取验证码）
         */
        public static final String IAM_AUTH_SMS_CAPTCHA_PHONE_LOGIN_GET_CAPTCHA_LOCK =
                "iam:auth:sms_captcha:phone_login_get_captcha_lock:";

        /**
         * 手机号登录短信验证码（提交）
         */
        public static final String IAM_AUTH_SMS_CAPTCHA_PHONE_LOGIN_SUBMIT_LOCK =
                "iam:auth:sms_captcha:phone_login_submit_lock:";

        /**
         * 忘记密码短信验证码（获取验证码）
         */
        public static final String IAM_AUTH_SMS_CAPTCHA_FORGET_PASSWORD_GET_CAPTCHA_LOCK =
                "iam:auth:sms_captcha:forget_password_get_captcha_lock:";

        /**
         * 忘记密码短信验证码（修改密码）
         */
        public static final String IAM_AUTH_SMS_CAPTCHA_FORGET_PASSWORD_UPDATE_PASSWORD_LOCK =
                "iam:auth:sms_captcha:forget_password_update_password_lock:";


    }

    public static class Data {

        /**
         * 区域树
         */
        public static final String DATA_AREA_TREE_LOCK = "data:area:tree:lock";

        /**
         * 组织树
         */
        public static final String DATA_ORGANIZATION_TREE_LOCK = "data:organization:tree:lock:";

    }

    public static class Hrm {
        /**
         * 部门树
         */
        public static final String HRM_DEPARTMENT_TREE_LOCK = "hrm:department:tree:lock";

        /**
         * 同步北森职务
         */
        public static final String HRM_SYNC_BEI_SEN_JOB_POST_LOCK = "hrm:sync:beiSen:jobPost:lock";

        /**
         * 同步北森部门
         */
        public static final String HRM_SYNC_BEI_SEN_DEPARTMENT_LOCK = "hrm:sync:beiSen:department:lock";

        /**
         * 同步北森员工
         */
        public static final String HRM_SYNC_BEI_SEN_EMPLOYEE_LOCK = "hrm:sync:beiSen:employee:lock";
    }

}
