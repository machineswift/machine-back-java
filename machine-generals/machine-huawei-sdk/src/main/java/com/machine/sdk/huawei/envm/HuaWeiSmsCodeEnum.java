package com.machine.sdk.huawei.envm;

import com.machine.sdk.common.envm.BaseEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum HuaWeiSmsCodeEnum implements BaseEnum<HuaWeiSmsCodeEnum, String> {

    SUCCESS("000000", "请求成功。接口调用成功，请等待短信状态报告。"),

    SYSTEM_EXCEPTION("E000000", "表示系统异常，一般是请求格式异常，短信平台无法解析。"),

    HTTP_AUTHORIZATION_NOT_FOUND("E000001", "HTTP消息头未找到Authorization字段。"),

    AUTHORIZATION_REALM_NOT_FOUND("E000002", "Authorization字段中未找到realm属性。"),

    AUTHORIZATION_PROFILE_NOT_FOUND("E000003", "Authorization字段中未找到profile属性。"),

    AUTHORIZATION_REALM_VALUE_WRONG("E000004", "Authorization中realm属性值应该为“SDP”。"),

    AUTHORIZATION_PROFILE_VALUE_WRONG("E000005", "Authorization中profile属性值应该为“UsernameToken”。"),

    AUTHORIZATION_TYPE_VALUE_WRONG("E000006", "Authorization中type属性值应该为“Appkey”。"),

    AUTHORIZATION_TYPE_NOT_FOUND("E000007", "Authorization字段中未找到type属性。"),

    AUTHORIZATION_WSSE_NOT_FOUND("E000008", "Authorization中没有携带WSSE。"),

    HTTP_X_WSSE_NOT_FOUND("E000020", "HTTP头未找到X-WSSE字段。"),

    X_WSSE_USERNAME_NOT_FOUND("E000021", "X-WSSE字段中未找到UserName属性。"),

    X_WSSE_NONCE_NOT_FOUND("E000022", "X-WSSE字段中未找到Nonce属性。"),

    X_WSSE_CREATED_NOT_FOUND("E000023", "X-WSSE字段中未找到Created属性。"),

    X_WSSE_PASSWORD_DIGEST_NOT_FOUND("E000024", "X-WSSE字段中未找到PasswordDigest属性。"),

    X_WSSE_CREATED_FORMAT_WRONG("E000025", "Created属性格式错误。"),

    X_WSSE_USERNAME_TOKEN_NOT_FOUND("E000026", "X-WSSE字段中未找到UsernameToken属性。"),

    ILLEGAL_REQUEST("E000027", "非法请求。"),

    CONTENT_TYPE_WRONG("E000040", "ContentType值应该为application/x-www-form-urlencoded。"),

    X_SDK_DATE_EMPTY("E000041", "X-Sdk-Date为空。"),

    X_SDK_DATE_FORMAT_WRONG("E000042", "X-Sdk-Date格式错误。"),

    AUTHORIZATION_FORMAT_WRONG("E000043", "Authorization格式错误。"),

    X_SDK_DATE_EXPIRED("E000044", "X-Sdk-Date过期。"),

    AUTHORIZATION_CHECK_FAILED("E000045", "Authorization校验失败。"),

    AUTHENTICATION_FAILED("E000101", "鉴权失败。"),

    APP_KEY_INVALID("E000102", "app_key无效。"),

    APP_KEY_UNAVAILABLE("E000103", "app_key不可用。"),

    APP_SECRET_INVALID("E000104", "app_secret无效。"),

    PASSWORD_DIGEST_INVALID("E000105", "PasswordDigest无效。"),

    APP_KEY_NO_API_PERMISSION("E000106", "app_key没有调用本API的权限。"),

    USER_STATUS_NOT_ACTIVATED("E000109", "用户状态未激活。"),

    TIME_OUT_OF_LIMIT("E000109", "时间超出限制。"),

    USERNAME_PASSWORD_WRONG("E000111", "用户名或密码错误。"),

    USER_STATUS_FROZEN("E000112", "用户状态已冻结。"),

    PARAMETER_FORMAT_WRONG("E000503", "参数格式错误。"),

    SMS_SEND_FAILED("E000510", "表示短信发送失败，失败原因请查看status参数。"),

    APP_IP_NOT_IN_WHITELIST("E000620", "对端app IP不在白名单列表中。"),

    SMS_SEND_QUOTA_REACHED("E000623", "短信发送量达到限额。"),

    SMS_SEND_QUOTA_REACHED_NATIONAL("E000630", "短信发送量达到国家/地区级限额。");


    private final String code;
    private final String message;

    @Override
    public String getName() {
        return this.name();
    }
}
