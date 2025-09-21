package com.machine.app.openapi.dictionary.business.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.collection.ListUtil;
import cn.hutool.core.lang.ClassScanner;
import com.machine.app.openapi.dictionary.business.IOpenApiEnumBusiness;
import com.machine.app.openapi.dictionary.controller.request.OpenApiEnumQueryEnumInfoRequestVo;
import com.machine.app.openapi.dictionary.controller.response.OpenApiEnumInfoResponse;
import com.machine.sdk.common.envm.BaseEnum;
import com.machine.sdk.common.exception.BusinessException;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.*;

@Slf4j
@Component
public class OpenApiEnumBusinessImpl implements IOpenApiEnumBusiness {

    @Override
    public List<OpenApiEnumInfoResponse> queryEnumInfo(OpenApiEnumQueryEnumInfoRequestVo request) {
        List<OpenApiEnumInfoResponse> responseList =  ENUM_MAP.get(request.getEnumName());
        if (CollectionUtil.isEmpty(responseList)) {
            return ListUtil.empty();
        }
        return responseList;
    }

    @PostConstruct
    public void init() {
        for (int i = 0; i < ENUM_BASE_PACKAGES.length; i++) {
            Set<Class<?>> enumClassSet = ClassScanner.scanPackage(ENUM_BASE_PACKAGES[i]);
            for (Class<?> clazz : enumClassSet) {
                if (clazz.isInterface() || !clazz.isEnum()) {
                    continue;
                }

                if (ENUM_MAP.containsKey(clazz.getSimpleName())) {
                    throw new BusinessException("iam.dictionary.enumNameAlreadyExists", "枚举名称重复");
                }

                List<OpenApiEnumInfoResponse> responseList = new ArrayList<>();
                BaseEnum<?, String>[] enums = (BaseEnum<?, String>[]) clazz.getEnumConstants();

                if (enums != null) {
                    for (BaseEnum<?, String> e : enums) {
                        OpenApiEnumInfoResponse response = new OpenApiEnumInfoResponse();
                        response.setCode(e.toString());
                        response.setMessage(e.getMessage());
                        responseList.add(response);
                    }
                    ENUM_MAP.put(clazz.getSimpleName(), responseList);
                }
            }
        }
    }

    private static final String[] ENUM_BASE_PACKAGES = {
            "com.xijie.sdk.common.envm",
            "com.xijie.sdk.beisen.envm"
    };

    private final Map<String, List<OpenApiEnumInfoResponse>> ENUM_MAP = new HashMap<>();
}

