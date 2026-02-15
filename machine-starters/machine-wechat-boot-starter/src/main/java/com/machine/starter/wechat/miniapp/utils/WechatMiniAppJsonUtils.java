package com.machine.starter.wechat.miniapp.utils;

import com.fasterxml.jackson.annotation.JsonInclude;
import tools.jackson.databind.json.JsonMapper;

public class WechatMiniAppJsonUtils {

    private static final tools.jackson.databind.ObjectMapper JSON = JsonMapper.builder()
            .changeDefaultPropertyInclusion(
                    incl -> incl.withValueInclusion(JsonInclude.Include.NON_NULL)
                            .withContentInclusion(JsonInclude.Include.NON_NULL)
            )
            .enable(tools.jackson.databind.SerializationFeature.INDENT_OUTPUT)
            .build();

    public static String toJson(Object obj) {
        return JSON.writeValueAsString(obj);
    }

}
