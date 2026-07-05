package com.machine.starter.obs.validate;

import com.machine.sdk.base.envm.base.ModuleEntityEnum;
import com.machine.sdk.base.exception.data.DataObsBusinessException;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class ModuleEntityValidatorRegistry {

    private final Map<ModuleEntityEnum, IModuleEntityValidator> validatorMap = new HashMap<>();

    @Autowired
    private List<IModuleEntityValidator> validators;

    @PostConstruct
    public void init() {
        for (IModuleEntityValidator validator : validators) {
            validatorMap.put(validator.getSupportedEnum(), validator);
        }
    }

    public void validateAttachmentGroup(ModuleEntityEnum entityEnum,
                                        String attachmentGroup) {
        IModuleEntityValidator validate = validatorMap.get(entityEnum);
        if (null == validate) {
            throw new DataObsBusinessException("data.obs.validate.factory.notImplValidate", "未实现对应的模块枚举校验");
        }

        validate.validateAttachmentGroup(attachmentGroup);
    }

    public void validateEntityId(ModuleEntityEnum entityEnum,
                                 String entityId) {

        if (null == entityEnum) {
            throw new DataObsBusinessException("data.obs.validate.factory.nullEntityEnum", "模块实体枚举为空");
        }

        IModuleEntityValidator validate = validatorMap.get(entityEnum);
        if (null == validate) {
            throw new DataObsBusinessException("data.obs.validate.factory.notImplValidate", "未实现对应的模块枚举校验");
        }

        validate.validateEntityId(entityId);
    }

}
