package com.machine.starter.wechat.cp.utils;

import com.fasterxml.jackson.annotation.JsonInclude;
import tools.jackson.databind.ObjectMapper;
import tools.jackson.databind.SerializationFeature;
import tools.jackson.databind.json.JsonMapper;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class WechatCpJsonUtils {

    private static final ObjectMapper JSON = JsonMapper.builder()
            .changeDefaultPropertyInclusion(
                    incl -> incl.withValueInclusion(JsonInclude.Include.NON_NULL)
                            .withContentInclusion(JsonInclude.Include.NON_NULL)
            )
            .enable(SerializationFeature.INDENT_OUTPUT)
            .build();

    public static String toJson(Object obj) {
        return JSON.writeValueAsString(obj);
    }
}