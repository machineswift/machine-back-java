package com.machine.sdk.base.annotation;

import com.machine.sdk.base.annotation.validator.StrongPasswordValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.*;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = StrongPasswordValidator.class)
@Documented
public @interface StrongPassword {
    String message() default "密码长度必须大于等于64位，且至少包含4个特殊字符";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
