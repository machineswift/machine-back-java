package com.machine.sdk.common.tool;

import cn.hutool.core.util.RandomUtil;

import java.util.Random;

public class StringUtil {

    public static String verificationCode() {
        Random random = new Random();
        // 生成 0 到 999999 之间的随机整数
        int randomNumber = random.nextInt(1000000);
        // 使用 String.format 方法将随机数格式化为 6 位字符串，不足 6 位时前面补 0
        return String.format("%06d", randomNumber);
    }

    public static String generatePassword() {
        String upperCaseLetters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        String lowerCaseLetters = "abcdefghijklmnopqrstuvwxyz";
        String specialChars = ".*-+@#";

        //生成随机密码
        String upperCase = RandomUtil.randomString(upperCaseLetters, 3);
        String lowerCase = RandomUtil.randomString(lowerCaseLetters, 3);
        String special = RandomUtil.randomString(specialChars, 2);
        String originalPassword = upperCase + lowerCase + special;

        //打乱顺序
        char[] charArray = originalPassword.toCharArray();
        Random random = new Random();
        for (int i = charArray.length - 1; i > 0; i--) {
            int j = random.nextInt(i + 1);
            char temp = charArray[i];
            charArray[i] = charArray[j];
            charArray[j] = temp;
        }

        //返回打乱后的密码
        return "{noop}" + new String(charArray);
    }
}
