package com.machine.starter.web.accessLog.annotation;

import com.machine.sdk.base.envm.base.ModuleEntityEnum;
import com.machine.sdk.base.envm.base.ModuleEnum;
import com.machine.sdk.base.envm.base.audit.ActionTypeEnum;
import com.machine.sdk.base.envm.base.audit.OperateSourceEnum;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 访问日志注解
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface WebApiAccessLog {

    /**
     * 是否记录访问日志
     */
    boolean enable() default true;

    /**
     * 操作信息
     */
    OperateSourceEnum operateSource();

    ModuleEnum module();

    ModuleEntityEnum moduleEntity();

    ActionTypeEnum operateType();

    String operateName();

    /**
     * 是否记录请求/相应参数
     */
    boolean requestEnable() default true;

    boolean responseEnable() default false;

    /**
     * 敏感参数数组
     */
    String[] sanitizeKeys() default {};

}
