package com.machine.app.iam.dictionary.business.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.collection.ListUtil;
import cn.hutool.core.lang.ClassScanner;
import com.machine.app.iam.dictionary.business.IIamEnumBusiness;
import com.machine.app.iam.dictionary.controller.request.IamDictionaryEnumRequestVo;
import com.machine.app.iam.dictionary.controller.response.IamDictionaryEnumInfoResponse;
import com.machine.sdk.common.envm.BaseEnum;
import com.machine.sdk.common.exception.BusinessException;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.*;

@Slf4j
@Component
public class IamEnumBusinessImpl implements IIamEnumBusiness {


    @Override
    public List<IamDictionaryEnumInfoResponse> queryEnumInfo(IamDictionaryEnumRequestVo request) {
        List<IamDictionaryEnumInfoResponse> responseList = ENUM_MAP.get(request.getEnumName());
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

                List<IamDictionaryEnumInfoResponse> responseList = new ArrayList<>();
                BaseEnum<?, String>[] enums = (BaseEnum<?, String>[]) clazz.getEnumConstants();

                if (enums != null) {
                    for (BaseEnum<?, String> e : enums) {
                        IamDictionaryEnumInfoResponse response = new IamDictionaryEnumInfoResponse();
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
            "com.machine.sdk.common.envm",
            "com.machine.sdk.beisen.envm"
    };

    private final Map<String, List<IamDictionaryEnumInfoResponse>> ENUM_MAP = new HashMap<>();
}

