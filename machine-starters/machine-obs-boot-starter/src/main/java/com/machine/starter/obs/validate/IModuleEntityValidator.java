package com.machine.starter.obs.validate;

import com.machine.sdk.base.envm.base.ModuleEntityEnum;

public interface IModuleEntityValidator {

    ModuleEntityEnum getSupportedEnum();

    void validateAttachmentGroup(String attachmentGroup);

    void validateEntityId(String entityId);

}
