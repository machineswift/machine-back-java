package com.machine.sdk.common.envm;

public interface BaseEnum<T extends Enum<T>, E> {

    String getName();

    E getCode();

    String getMessage();

    /**
     * 根据Code获取对应IBaseEnum实例
     *
     * @param clazz 枚举类
     * @param code  编码
     * @param <T>   枚举类
     * @return 枚举类实例
     */
    static <T extends Enum<T>> T getInstance(Class<T> clazz, String code) {
        if (!clazz.isEnum() || !BaseEnum.class.isAssignableFrom(clazz)) {
            throw new IllegalArgumentException("未知的枚举编码：" + code);
        }
        T[] enumConstants = clazz.getEnumConstants();
        if (enumConstants != null) {
            for (T enumConstant : enumConstants) {
                BaseEnum<?, ?> baseEnum = (BaseEnum<?, ?>) enumConstant;
                if (baseEnum.getCode().equals(code)) {
                    return enumConstant;
                }
            }
        }
        throw new IllegalArgumentException("未知的枚举编码：" + code);
    }

}
