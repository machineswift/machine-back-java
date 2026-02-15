package com.machine.sdk.common.tool;

import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;

import java.util.ArrayList;
import java.util.List;

public class JsonUtil {

    /**
     * 安全地解析 JSON 字符串到对象
     */
    public static <T> T safeToBean(String jsonStr, Class<T> beanClass) {
        if (StrUtil.isBlank(jsonStr)) {
            return null;
        }

        String cleanedJson = forceFixJson(jsonStr);
        return JSONUtil.toBean(cleanedJson, beanClass);
    }

    /**
     * 安全地解析 JSON 字符串到对象数组
     */
    public static <T> List<T> safeToList(String jsonStr, Class<T> elementClass) {
        if (StrUtil.isBlank(jsonStr)) {
            return new ArrayList<>();
        }

        String cleanedJson = forceFixJson(jsonStr);
        return JSONUtil.toList(cleanedJson, elementClass);
    }


    /**
     * 强力修复 JSON 字符串
     */
    public static String forceFixJson(String jsonStr) {
        if (StrUtil.isBlank(jsonStr)) {
            return jsonStr;
        }

        String result = jsonStr.trim();

        // 去除外层引号（如果有）
        if (result.startsWith("\"") && result.endsWith("\"")) {
            result = result.substring(1, result.length() - 1);
        }

        // 替换所有转义字符
        result = result.replace("\\\"", "\"")
                .replace("\\\\", "\\")
                .replace("\\n", "\n")
                .replace("\\r", "\r")
                .replace("\\t", "\t")
                .replace("\\b", "\b")
                .replace("\\f", "\f");

        return result;
    }
}