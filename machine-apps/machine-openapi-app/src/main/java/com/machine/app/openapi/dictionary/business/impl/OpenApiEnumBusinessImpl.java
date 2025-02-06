package com.machine.app.openapi.dictionary.business.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.collection.ListUtil;
import cn.hutool.core.lang.ClassScanner;
import com.machine.app.openapi.dictionary.business.IOpenApiEnumBusiness;
import com.machine.app.openapi.dictionary.controller.request.OpenApiEnumQueryEnumInfoRequestVo;
import com.machine.app.openapi.dictionary.controller.response.OpenApiEnumInfoResponse;
import com.machine.sdk.common.envm.BaseEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.*;

@Slf4j
@Component
public class OpenApiEnumBusinessImpl implements IOpenApiEnumBusiness {

    @Override
    public List<OpenApiEnumInfoResponse> queryEnumInfo(OpenApiEnumQueryEnumInfoRequestVo request) {
        List<OpenApiEnumInfoResponse> responseList = getEnumInfos(request.getEnumName());
        if (CollectionUtil.isEmpty(responseList)) {
            return ListUtil.empty();
        }
        return responseList;
    }

    private List<OpenApiEnumInfoResponse> getEnumInfos(String enumName) {
        if (!ENUM_MAP.isEmpty()) {
            return ENUM_MAP.get(enumName);
        }
        synchronized (lock) {
            if (!ENUM_MAP.isEmpty()) {
                return ENUM_MAP.get(enumName);
            }

            for (int i = 0; i < ENUM_BASE_PACKAGES.length; i++) {
                Set<Class<?>> enumClassSet = ClassScanner.scanPackage(ENUM_BASE_PACKAGES[i]);
                for (Class<?> clazz : enumClassSet) {
                    if (clazz.isInterface()) {
                        continue;
                    }
                    List<OpenApiEnumInfoResponse> responseList = new ArrayList<>();
                    BaseEnum<?, String>[] enums = (BaseEnum<?, String>[]) clazz.getEnumConstants();
                    for (BaseEnum<?, String> e : enums) {
                        OpenApiEnumInfoResponse response = new OpenApiEnumInfoResponse();
                        response.setCode(e.toString());
                        response.setMsg(e.getMsg());
                        responseList.add(response);
                    }
                    ENUM_MAP.put(clazz.getSimpleName(), responseList);
                }
            }

        }
        return ENUM_MAP.get(enumName);
    }

    private static final Object lock = new Object();

    private static final String[] ENUM_BASE_PACKAGES = {
            "com.machine.sdk.common.envm",
            "com.machine.sdk.beisen.envm"
    };

    private final Map<String, List<OpenApiEnumInfoResponse>> ENUM_MAP = new HashMap<>();

}

