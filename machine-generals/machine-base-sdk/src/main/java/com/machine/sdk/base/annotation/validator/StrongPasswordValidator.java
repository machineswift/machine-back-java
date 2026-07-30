package com.machine.sdk.base.annotation.validator;


import com.machine.sdk.base.annotation.StrongPassword;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.regex.Pattern;

public class StrongPasswordValidator implements ConstraintValidator<StrongPassword, String> {

    // 预编译正则：长度>=64 且 至少4个特殊字符
    private static final java.util.regex.Pattern PATTERN =
            Pattern.compile("^(?=(.*[!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>/?]){4,}).{64,}$");

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null) {
            return true;
        }
        return PATTERN.matcher(value).matches();
    }
}
