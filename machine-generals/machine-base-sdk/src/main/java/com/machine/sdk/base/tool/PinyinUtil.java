package com.machine.sdk.base.tool;

import cn.hutool.core.util.StrUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 拼音工具类
 * <p>
 * 基于 hutool 的 {@link cn.hutool.extra.pinyin.PinyinUtil} + pinyin4j 引擎实现，
 * 用于将中文名称转换为「拼音全拼」「拼音首字母」。
 * <p>
 * ⚠️ 多音字按 pinyin4j 字典取读音，可能不准确，业务侧允许手动修正。
 */
public class PinyinUtil {

    private static final Logger log = LoggerFactory.getLogger(PinyinUtil.class);

    private PinyinUtil() {
    }

    /**
     * 转换为拼音全拼（小写、无音调、无分隔符）
     *
     * @param text 原文（中文/英文/数字混合）
     * @return 拼音全拼；原文为空或转换异常时返回空串
     */
    public static String full(String text) {
        if (StrUtil.isBlank(text)) {
            return "";
        }
        try {
            return cn.hutool.extra.pinyin.PinyinUtil.getPinyin(text, "").toLowerCase();
        } catch (Exception e) {
            log.warn("拼音全拼转换失败,text:{}", text, e);
            return "";
        }
    }

    /**
     * 转换为拼音首字母（小写）
     *
     * @param text 原文（中文/英文/数字混合）
     * @return 拼音首字母；原文为空或转换异常时返回空串
     */
    public static String initial(String text) {
        if (StrUtil.isBlank(text)) {
            return "";
        }
        try {
            return cn.hutool.extra.pinyin.PinyinUtil.getFirstLetter(text, "").toLowerCase();
        } catch (Exception e) {
            log.warn("拼音首字母转换失败,text:{}", text, e);
            return "";
        }
    }
}
