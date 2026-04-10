package com.machine.starter.obs.validate;

import com.machine.sdk.base.envm.base.ModuleEntityEnum;
import com.machine.sdk.base.exception.data.DataFileBusinessException;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class ModuleEntityValidateFactory {

    private final Map<ModuleEntityEnum, IModuleEntityValidate> validatorMap = new HashMap<>();

    @Autowired
    private List<IModuleEntityValidate> validators;

    @PostConstruct
    public void init() {
        for (IModuleEntityValidate validator : validators) {
            validatorMap.put(validator.getSupportedEnum(), validator);
        }
    }

    public void validate(ModuleEntityEnum entityEnum,
                         String entityId) {

        if (null == entityEnum) {
            throw new DataFileBusinessException("data.obs.validate.factory.nullEntityEnum", "模块实体枚举为空");
        }

        IModuleEntityValidate validate = validatorMap.get(entityEnum);
        if (null == validate) {
            throw new DataFileBusinessException("data.obs.validate.factory.notImplValidate", "未实现对应的模块枚举校验");
        }

        validate.validate(entityId);
    }

}
