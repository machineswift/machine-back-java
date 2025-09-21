package com.machine.sdk.common.tool;
import com.machine.sdk.common.envm.BaseEnum;

public class BaseEnumUtil {

    public static <T extends BaseEnum> T getEnumByCode(Class<T> tClass, String code) {
        for (T t : tClass.getEnumConstants()) {
            if (String.valueOf(t.getCode()).equals(code)) {
                return t;
            }
        }
        return null;
    }


    public static <T extends BaseEnum> String getMessageByCode(Class<T> tClass, String code) {
        for (T t : tClass.getEnumConstants()) {
            if (t.getCode().equals(code)) {
                return t.getMessage();
            }
        }
        return null;
    }


    public static <T extends BaseEnum> Object getCodeByMessage(Class<T> tClass, String message) {
        for (T t : tClass.getEnumConstants()) {
            if (t.getMessage().equals(message)) {
                return t.getCode();
            }
        }
        return null;
    }
}