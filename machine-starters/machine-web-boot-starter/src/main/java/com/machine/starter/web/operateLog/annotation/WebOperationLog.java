package com.machine.starter.web.operateLog.annotation;

import com.machine.sdk.base.envm.base.ModuleEntityEnum;
import com.machine.sdk.base.envm.base.ModuleEnum;
import com.machine.sdk.base.envm.base.audit.ActionTypeEnum;
import com.machine.sdk.base.envm.base.audit.OperateSourceEnum;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 业务操作日志注解
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface WebOperationLog {

    /**
     * 是否记录操作日志
     */
    boolean enable() default true;

    /**
     * 操作来源 {@link OperateSourceEnum}
     */
    OperateSourceEnum operateSource();

    /**
     * 操作模块 {@link ModuleEnum}
     */
    ModuleEnum module();

    /**
     * 操作模块实体 {@link ModuleEntityEnum}
     */
    ModuleEntityEnum moduleEntity();

    /**
     * 操作分类(增/删/改/查/授权等) {@link ActionTypeEnum}
     */
    ActionTypeEnum operateType();

    /**
     * 操作名称，如：修改用户权限
     */
    String operateName();

    /**
     * 操作模块实体主键 SpEL，如 "#request.id"
     */
    String moduleEntityId();

    /**
     * 操作内容描述
     */
    String content() default "";

    /**
     * 是否做变更 diff
     */
    boolean diff() default true;

    /**
     * diff 忽略字段
     */
    String[] ignoreFields() default {};

    /**
     * 敏感字段脱敏 key（对请求/响应体快照中对应 JSON key 掩码）
     */
    String[] sanitizeKeys() default {};

    /**
     * 是否记录响应体快照（默认关闭，避免大字段）
     */
    boolean responseEnable() default false;
}
