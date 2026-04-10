package com.machine.starter.obs.validate;

import com.machine.sdk.base.envm.base.ModuleEntityEnum;

public interface IModuleEntityValidate {

    ModuleEntityEnum getSupportedEnum();

    void validate(String entityId);

}
