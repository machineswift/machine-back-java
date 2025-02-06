package com.machine.starter.wechat.mp.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class WechatMpJsonUtils {
    public static String toJson(Object obj) {
        Gson gson = new GsonBuilder()
            .setPrettyPrinting()
            .disableHtmlEscaping()
            .create();
        return gson.toJson(obj);
    }
}
