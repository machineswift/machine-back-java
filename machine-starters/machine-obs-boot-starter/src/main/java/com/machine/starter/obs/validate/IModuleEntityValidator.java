package com.machine.starter.obs.validate;

import com.machine.sdk.base.envm.base.ModuleEntityEnum;
import com.machine.sdk.base.model.dto.IdNameDto;

public interface IModuleEntityValidator {

    ModuleEntityEnum getSupportedEnum();

    void validateAttachmentGroup(String attachmentGroup);

    void validateEntityId(String entityId);

    IdNameDto getNameInfo(String entityId);
}
