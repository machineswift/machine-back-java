package com.machine.sdk.common.constant;

public class PatternConstant {

    /**
     * 数字组成，长度在4到20个字符之间。
     */
    public static final String PHONE_PATTERN = "^\\d{8,16}$";


    /**
     * 字母、数字和下划线、点、短横线组成，长度在4到20个字符之间。
     */
    public static final String USER_NAME_PATTERN = "^[a-zA-Z0-9_.-]{4,20}$";

    /**
     * ^(?=.*[0-9])：确保密码中至少有一个数字。
     * (?=.*[a-z])：确保密码中至少有一个小写字母。
     * (?=.*[A-Z])：确保密码中至少有一个大写字母。
     * (?=.*[!@#$%^&*(),.?\":{}|<>])：确保密码中至少有一个特殊字符。
     * [A-Za-z0-9!@#$%^&*(),.?\":{}|<>]{8,20}：确保密码由上述字符组成，长度在8到20个字符之间。
     * $：确保字符串以这些规则结束。
     */
    public static final String PASSWORD_PATTERN = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#$%^&*(),.?\":{}|<>])[A-Za-z0-9!@#$%^&*(),.?\":{}|<>]{8,20}$";

}
